package org.koiki.beepro.dancer.intellij.dance.message

import com.intellij.openapi.editor.event.DocumentEvent

data class Change(
        val from: ChangeFromTo,
        val to: ChangeFromTo,
        val text: String
) {
    companion object {
        fun create(
                event: DocumentEvent
        ): Change =
                Change(
                        from = ChangeFromTo.create(event, ChangeType.from),
                        to = ChangeFromTo.create(event, ChangeType.to),
                        text = event.newFragment.toString()
                )
    }
}
