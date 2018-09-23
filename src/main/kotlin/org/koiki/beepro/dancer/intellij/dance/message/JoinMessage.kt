package org.koiki.beepro.dancer.intellij.dance.message

data class JoinMessage(
        override val type: MessageType = MessageType.join,
        val user: User
) : Message(
        type = type
)

data class User(val id: String, val icon: String)