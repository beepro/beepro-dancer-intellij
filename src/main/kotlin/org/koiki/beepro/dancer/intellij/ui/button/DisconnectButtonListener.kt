package org.koiki.beepro.dancer.intellij.ui.button

import org.koiki.beepro.dancer.intellij.dance.DanceOperation
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class DisconnectButtonListener(
        private val danceOperation: DanceOperation
) : ActionListener {

    override fun actionPerformed(e: ActionEvent?) = danceOperation.close()
}
