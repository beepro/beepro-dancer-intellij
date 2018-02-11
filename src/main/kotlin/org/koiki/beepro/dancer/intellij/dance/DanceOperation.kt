package org.koiki.beepro.dancer.intellij.dance

import com.intellij.util.messages.Topic
import org.koiki.beepro.dancer.intellij.dance.message.Message

interface DanceOperation {
    companion object {
        val CONNECTED_TOPIC: Topic<DanceOperation> = Topic.create("custom name", DanceOperation::class.java)
    }

    fun connect(uri: String)
    fun sendMessage(message: Message)
    fun close()
    fun onMessage(message: String)
}