package org.koiki.beepro.dancer.intellij.dance.message

data class Change(
        val from: ChangeFromTo,
        val to: ChangeFromTo,
        val text: String
)