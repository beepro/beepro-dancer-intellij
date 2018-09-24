package org.koiki.beepro.dancer.intellij.ui.button

import com.intellij.openapi.project.Project
import org.koiki.beepro.dancer.intellij.dance.DanceOperations
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.net.URI
import java.net.URISyntaxException
import javax.swing.JTextField

class ConnectButtonListener(
        private val textField: JTextField,
        private val project: Project
) : ActionListener {

    override fun actionPerformed(e: ActionEvent?) = project.messageBus
            .syncPublisher(DanceOperations.CONNECTED_TOPIC)
            .connect(toUri(textField.text))

    private fun toUri(uri: String): URI =
            try {
                URI(uri)
            } catch (e: URISyntaxException) {
                //TODO validation error should be shown in UI
                throw RuntimeException("TODO")
            }
}