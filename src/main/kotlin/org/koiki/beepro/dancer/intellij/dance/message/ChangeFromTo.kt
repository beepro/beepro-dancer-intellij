package org.koiki.beepro.dancer.intellij.dance.message

import com.intellij.openapi.editor.event.DocumentEvent

class ChangeFromTo {
    val row: Int
    val col: Int

    constructor(event: DocumentEvent, changeType: ChangeType) {
        val document = event.document

        if (event.newLength > 0) {
            val offset = event.offset

            this.row = document.getLineNumber(offset)
            this.col =
                    if (row == 0)
                        offset
                    else
                        offset - document.getLineEndOffset(row - 1)

        } else if (changeType == ChangeType.from) {
            val offset = event.offset

            this.row = document.getLineNumber(offset)
            this.col =
                    if (row == 0)
                        offset
                    else
                        offset - document.getLineEndOffset(row - 1)

        } else {
            val offset = event.offset + event.oldLength

            this.row = document.getLineNumber(offset)
            this.col =
                    if (row == 0)
                        offset
                    else
                        offset - document.getLineEndOffset(row - 1)
        }
    }
}

enum class ChangeType {
    from,
    to
}