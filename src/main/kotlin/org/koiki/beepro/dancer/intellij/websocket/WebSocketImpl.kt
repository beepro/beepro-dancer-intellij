package org.koiki.beepro.dancer.intellij.websocket

import org.glassfish.tyrus.client.ClientManager
import com.intellij.openapi.diagnostic.Logger
import java.net.URI
import javax.websocket.*
import javax.websocket.OnMessage

@ClientEndpoint
class WebSocketImpl : WebSocketInterface {
    private val log = Logger.getInstance(this::class.java)
    private var session: Session? = null

    override fun connect(uri: String) {
        log.info("I got notification by clicking button!, value: $uri")

        val websocketUri = URI(uri)
        val client = ClientManager.createClient()
        //val container = ContainerProvider.getWebSocketContainer()
        //container.connectToServer(this, websocketUri)
        client.connectToServer(this, websocketUri)
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