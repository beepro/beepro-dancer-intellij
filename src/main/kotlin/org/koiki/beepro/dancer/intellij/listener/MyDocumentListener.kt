package org.koiki.beepro.dancer.intellij.listener

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import org.koiki.beepro.dancer.intellij.dance.DanceOperationFactory
import org.koiki.beepro.dancer.intellij.dance.message.Change
import org.koiki.beepro.dancer.intellij.dance.message.ChangeType
import org.koiki.beepro.dancer.intellij.dance.message.JoinMessage
import org.koiki.beepro.dancer.intellij.dance.message.User

class MyDocumentListener : DocumentListener {
    private val log = Logger.getInstance(this::class.java)

    //TODO any ways to expose this under same package?
    constructor() {}

    override fun beforeDocumentChange(event: DocumentEvent) {
        val document = event.document

        // line number (starts from 0)
        log.info("document.getLineNumber(event.offset): ${document.getLineNumber(event.offset)}")
        log.info("beforeDocumentChange, event: ${event}")

        val changeFrom = Change(event, ChangeType.from)
        val changeTo = Change(event, ChangeType.to)
        log.info("changeFrom row: ${changeFrom.row}, col: ${changeFrom.col}")
        log.info("changeTo   row: ${changeTo.row}, col: ${changeTo.col}")

        val webSocketClient = DanceOperationFactory.getInstance()
        webSocketClient.sendMessage(JoinMessage(user = User(id = "1", icon = "aaa")))
    }

    override fun documentChanged(event: DocumentEvent) {
        //log.info("documentChanged, event: ${event}")
    }
}