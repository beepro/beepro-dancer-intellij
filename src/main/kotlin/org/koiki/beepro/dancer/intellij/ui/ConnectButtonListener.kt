package org.koiki.beepro.dancer.intellij.ui

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import org.koiki.beepro.dancer.intellij.websocket.WebSocketInterface
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JTextField

class ConnectButtonListener : ActionListener {
    private val log = Logger.getInstance(this::class.java)
    private val textField: JTextField
    private val project: Project

    constructor(textField: JTextField, project: Project) {
        this.textField = textField
        this.project = project
    }

    override fun actionPerformed(e: ActionEvent) {
        val uri = textField.text
        log.info("clicked!, uri: $uri")

        val connectedNotifier = project.messageBus.syncPublisher(WebSocketInterface.CONNECTED_TOPIC)
        connectedNotifier.connect(textField.text)
    }
}