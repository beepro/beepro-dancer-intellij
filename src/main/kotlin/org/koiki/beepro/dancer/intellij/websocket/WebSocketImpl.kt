package org.koiki.beepro.dancer.intellij.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import org.glassfish.tyrus.client.ClientManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.util.concurrency.AppExecutorUtil
import org.koiki.beepro.dancer.intellij.websocket.message.JoinMessage
import org.koiki.beepro.dancer.intellij.websocket.message.Message
import org.koiki.beepro.dancer.intellij.websocket.message.User
import java.net.URI
import java.util.concurrent.TimeUnit
import javax.websocket.*
import javax.websocket.OnMessage

@ClientEndpoint
class WebSocketImpl : WebSocketInterface {
    private val log = Logger.getInstance(this::class.java)
    private var session: Session? = null
    private val objectMapper = ObjectMapper()

    override fun connect(uri: String) {
        log.info("I got notification by clicking button!, value: $uri")

        val websocketUri = URI(uri)
        val client = ClientManager.createClient()
        //val container = ContainerProvider.getWebSocketContainer()
        //container.connectToServer(this, websocketUri)
        client.connectToServer(this, websocketUri)

        sendMessage(JoinMessage(user = User(id = "ninja", icon = "hoge.png")))

        AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(
                {
                    this.keepAlive()
                }, 1, 5, TimeUnit.SECONDS)
    }

    fun sendMessage(message: Message) {
        log.info("send message: ${objectMapper.writeValueAsString(message)}")

        session?.asyncRemote?.sendObject(objectMapper.writeValueAsString(message))
    }

    private fun keepAlive() {
        log.info("sending keepalive")
        session?.asyncRemote?.sendObject("KEEPALIVE")
    }

    fun close() {
        session?.close()
        log.info("session closed")
    }

    @OnOpen
    fun onOpen(session: Session) {
        log.info("WebSocket connection is established, session: $session")
        this.session = session
    }

    @OnMessage
    fun onMessage(message: String) {
        log.info("message got")
    }

    @OnClose
    fun onClose(session: Session, closeReason: CloseReason) {
        log.info("closed")
    }
}