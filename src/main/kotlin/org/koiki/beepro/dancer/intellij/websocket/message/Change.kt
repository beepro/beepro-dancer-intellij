package org.koiki.beepro.dancer.intellij.websocket.message

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.event.DocumentEvent

class Change {
    private val log = Logger.getInstance(this::class.java)

    val row: Int
    val col: Int

    constructor(event: DocumentEvent) {
        val document = event.document
        val offset = event.offset + event.oldLength

        var row = 0
        var tmpCol = 0

        // inputting something
        //if (event.newLength > 0) {

            while (row != document.getLineNumber(offset)) {
                //log.info("document.getLineEndOffset($row): ${document.getLineEndOffset(row)}")
                tmpCol = offset - document.getLineEndOffset(row) - 1
                row++
            }

            this.row = row
            this.col = tmpCol

        // removing something
        //} else {

        //    this.row = row
        //    this.col = tmpCol
        //}
    }
}