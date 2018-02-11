package org.koiki.beepro.dancer.intellij.websocket

import com.intellij.util.messages.Topic

interface WebSocketOperation {
    companion object {
        val CONNECTED_TOPIC: Topic<WebSocketOperation> = Topic.create("custom name", WebSocketOperation::class.java)
    }

    fun connect(uri: String)
}