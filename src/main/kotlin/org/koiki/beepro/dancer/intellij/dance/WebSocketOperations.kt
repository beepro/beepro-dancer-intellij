package org.koiki.beepro.dancer.intellij.dance

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.util.concurrency.AppExecutorUtil
import org.glassfish.tyrus.client.ClientManager
import org.koiki.beepro.dancer.intellij.dance.message.*
import java.net.URI
import java.util.concurrent.TimeUnit
import javax.websocket.*

@ClientEndpoint
class WebSocketOperations(
        private val project: Project
) : DanceOperations {

    private val log = Logger.getInstance(this::class.java)

    private val objectMapper = ObjectMapper().registerModule(KotlinModule())

    private var session: Session? = null

    override fun connect(uri: URI) {
        log.info("I got notification by clicking button!, value: $uri")

        ClientManager.createClient().connectToServer(this, uri)

        sendMessage(JoinMessage(user = User(id = "ninja", icon = "hoge.png")))

        AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(
                {
                    this.keepAlive()
                }, 1, 5, TimeUnit.SECONDS)
    }

    override fun sendMessage(message: Message) {
        log.info("send message: ${objectMapper.writeValueAsString(message)}")

        session?.asyncRemote?.sendObject(objectMapper.writeValueAsString(message))
    }

    private fun keepAlive() {
        log.info("sending keepalive")
        session?.asyncRemote?.sendObject("KEEPALIVE")
    }

    override fun close() {
        session?.close()
        log.info("session closed")
    }

    @OnOpen
    fun onOpen(session: Session) {
        log.info("WebSocket connection is established, session: $session")
        this.session = session
    }

    @OnMessage
    override fun onMessage(message: String) {
        log.info("message got, $message")

        if (message != "KEEPALIVE") {
            val messageMapObj: Map<String, Any> = try {
                objectMapper.readValue(message)
            } catch (e: Exception) {
                log.error(e.message)
                throw e
            }
            when (messageMapObj["type"] as String) {
                MessageType.change.name ->
                    FileOperations.changeFile(objectMapper.readValue(message), project)
                else -> log.info("unknown message")
            }
        }
    }

    @OnClose
    fun onClose(session: Session, closeReason: CloseReason) {
        log.info("closed")
    }
}