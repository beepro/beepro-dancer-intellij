package org.koiki.beepro.dancer.intellij.dance

import com.intellij.util.messages.Topic
import org.koiki.beepro.dancer.intellij.dance.message.Message
import java.net.URI

interface DanceOperation {
    companion object {
        val CONNECTED_TOPIC: Topic<DanceOperation> = Topic.create("custom name", DanceOperation::class.java)
    }

    fun connect(uri: URI)
    fun sendMessage(message: Message)
    fun close()
    fun onMessage(message: String)
}
