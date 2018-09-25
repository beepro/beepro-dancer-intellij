package org.koiki.beepro.dancer.intellij.dance.message

import com.intellij.openapi.editor.event.DocumentEvent

data class ChangeFromTo(
    val row: Int,
    val col: Int
) {
    companion object {
        fun create(event: DocumentEvent, changeType: ChangeType): ChangeFromTo {
            val document = event.document

            when {
                event.newLength > 0 -> {
                    val offset = event.offset
                    val row = document.getLineNumber(offset)

                    return ChangeFromTo(
                            row = row,
                            col = if (row == 0) offset else offset - document.getLineEndOffset(row - 1)
                    )
                }

                changeType == ChangeType.from -> {
                    val offset = event.offset
                    val row = document.getLineNumber(offset)

                    return ChangeFromTo(
                            row = row,
                            col = if (row == 0) offset else offset - document.getLineEndOffset(row - 1)
                    )
                }

                else -> {
                    val offset = event.offset + event.oldLength
                    val row = document.getLineNumber(offset)

                    return ChangeFromTo(
                            row = row,
                            col = if (row == 0) offset else offset - document.getLineEndOffset(row - 1)
                    )
                }
            }
        }
    }
}

enum class ChangeType {
    from,
    to
}