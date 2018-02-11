package org.koiki.beepro.dancer.intellij.websocket

object WebSocketClientFactory {
    private val webSocketImpl = WebSocketClient()

    fun getInstance(): WebSocketClient {
        return this.webSocketImpl
    }
}