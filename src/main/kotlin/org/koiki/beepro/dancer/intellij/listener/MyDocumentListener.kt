package org.koiki.beepro.dancer.intellij.listener

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import org.koiki.beepro.dancer.intellij.websocket.message.Change
import org.koiki.beepro.dancer.intellij.websocket.message.ChangeType

class MyDocumentListener : DocumentListener {
    private val log = Logger.getInstance(this::class.java)

    //TODO this should be accessed from package
    protected constructor() {}

    override fun beforeDocumentChange(event: DocumentEvent) {
        val document = event.document

        // line number (starts from 0)
        log.info("document.getLineNumber(event.offset): ${document.getLineNumber(event.offset)}")
        log.info("beforeDocumentChange, event: ${event}")

        val changeFrom = Change(event, ChangeType.from)
        val changeTo = Change(event, ChangeType.to)
        log.info("changeFrom row: ${changeFrom.row}, col: ${changeFrom.col}")
        log.info("changeTo   row: ${changeTo.row}, col: ${changeTo.col}")
    }

    override fun documentChanged(event: DocumentEvent) {
        //log.info("documentChanged, event: ${event}")
    }
}