package org.koiki.beepro.dancer.intellij.websocket

import com.intellij.util.messages.Topic

interface WebSocketInterface {
    companion object {
        val CONNECTED_TOPIC: Topic<WebSocketInterface> = Topic.create("custom name", WebSocketInterface::class.java)
    }

    fun connect(uri: String)
}