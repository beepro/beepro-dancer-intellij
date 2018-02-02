package org.koiki.beepro.dancer.intellij.websocket.message

data class JoinMessage(val user: User) : Message() {
    val type = Type.join
}

data class User(val id: String, val icon: String)