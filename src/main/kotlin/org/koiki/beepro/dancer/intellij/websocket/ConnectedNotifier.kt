package org.koiki.beepro.dancer.intellij.websocket

import com.intellij.util.messages.Topic
import javax.naming.Context

interface ConnectedNotifier {
    companion object {
        val CONNECTED_TOPIC: Topic<ConnectedNotifier> = Topic.create("custom name", ConnectedNotifier::class.java)
    }

    fun afterAction()
}