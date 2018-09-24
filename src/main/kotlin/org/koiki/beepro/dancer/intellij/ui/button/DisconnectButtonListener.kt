package org.koiki.beepro.dancer.intellij.ui.button

import org.koiki.beepro.dancer.intellij.dance.DanceOperations
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class DisconnectButtonListener(
        private val danceOperations: DanceOperations
) : ActionListener {

    override fun actionPerformed(e: ActionEvent?) = danceOperations.close()
}
