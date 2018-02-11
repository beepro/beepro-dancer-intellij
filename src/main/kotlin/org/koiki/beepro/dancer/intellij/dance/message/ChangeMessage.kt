package org.koiki.beepro.dancer.intellij.dance.message

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.event.DocumentEvent

class Change {
    private val log = Logger.getInstance(this::class.java)

    val row: Int
    val col: Int

    constructor(event: DocumentEvent, changeType: ChangeType) {
        val document = event.document

        if (event.newLength > 0) {
            val offset = event.offset

            val row = document.getLineNumber(offset)
            val col = if (row == 0) offset else offset - document.getLineEndOffset(row - 1)

            this.row = row
            this.col = col

        } else if (changeType == ChangeType.from) {
            val offset = event.offset

            val row = document.getLineNumber(offset)
            val col = if (row == 0) offset else offset - document.getLineEndOffset(row - 1)

            this.row = row
            this.col = col

        } else {
            val offset = event.offset + event.oldLength

            val row = document.getLineNumber(offset)
            val col = if (row == 0) offset else offset - document.getLineEndOffset(row - 1)

            this.row = row
            this.col = col
        }
    }
}

enum class ChangeType {
    from,
    to
}