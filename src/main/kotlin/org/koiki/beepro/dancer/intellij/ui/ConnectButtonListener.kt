package org.koiki.beepro.dancer.intellij.ui

import com.intellij.openapi.diagnostic.Logger
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JFormattedTextField
import javax.swing.JTextField

class ConnectButtonListene : ActionListener {
    val log = Logger.getInstance(this::class.java)
    val textField: JTextField

    constructor(textField: JTextField) {
        this.textField = textField
    }

    override fun actionPerformed(e: ActionEvent) {
        val uri = textField.getText()
        log.info("clicked!, uri: $uri")
    }
}