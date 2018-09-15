package org.koiki.beepro.dancer.intellij.ui

import com.intellij.openapi.project.Project
import org.koiki.beepro.dancer.intellij.dance.DanceOperation
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JTextField

class ConnectButtonListener(
        private val textField: JTextField,
        private val project: Project
) : ActionListener {

    override fun actionPerformed(e: ActionEvent) = project.messageBus
            .syncPublisher(DanceOperation.CONNECTED_TOPIC)
            .connect(textField.text)
}