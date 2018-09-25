package org.koiki.beepro.dancer.intellij.dance

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiManager
import org.koiki.beepro.dancer.intellij.dance.message.ChangeMessage
import org.koiki.beepro.dancer.intellij.listener.MyDocumentListener

object FileOperations {
    private val log = Logger.getInstance(this::class.java)

    fun changeFile(message: ChangeMessage, project: Project) {
        WriteCommandAction.runWriteCommandAction(project) {
            val document: Document = findDocument(message.path, project)

            val offset = document.getLineStartOffset(message.change.from.row) + message.change.from.col

            //TODO infinite loop happens because we could not make listener disable correctly.
            document.removeDocumentListener(MyDocumentListener(project))
            // insertString() overwrites text so replaceString is fine
            document.replaceString(offset, offset, message.change.text)
            document.addDocumentListener(MyDocumentListener(project))
        }
    }

    /**
     * This method should be invoked from an implementation of Runnable.class otherwise we will get blow error log.
     * "Read access is allowed from event dispatch thread or inside read-action only"
     */
    //TODO error handling
    private fun findDocument(fileRelativePath: String, project: Project): Document {
        val vFile = project.baseDir.findFileByRelativePath(fileRelativePath)!!
        val psiFile = PsiManager.getInstance(project).findFile(vFile)!!
        return PsiDocumentManager.getInstance(project).getDocument(psiFile)!!
    }
}
