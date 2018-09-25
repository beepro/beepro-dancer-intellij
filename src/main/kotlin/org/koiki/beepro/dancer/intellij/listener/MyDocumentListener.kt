package org.koiki.beepro.dancer.intellij.listener

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.koiki.beepro.dancer.intellij.dance.DanceOperationFactory
import org.koiki.beepro.dancer.intellij.dance.message.ChangeMessage

class MyDocumentListener(
        private val project: Project
) : DocumentListener {
    private val log = Logger.getInstance(this::class.java)

    override fun beforeDocumentChange(event: DocumentEvent) {
        //TODO null handling
        val vFile: VirtualFile = FileDocumentManager.getInstance().getFile(event.document)!!

        DanceOperationFactory.getInstance()
                .sendMessage(ChangeMessage.create(project, vFile, event))
    }

    override fun documentChanged(event: DocumentEvent) {
        //log.info("documentChanged, event: ${event}")
    }
}
