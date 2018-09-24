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
        val document: Document = findDocument(message.path, project)

        WriteCommandAction.runWriteCommandAction(project) {
            document.removeDocumentListener(MyDocumentListener(project))
            // insertString() overwrites text so replaceString is fine
            document.replaceString(0, 0, "inserted by dancer\n")
            document.addDocumentListener(MyDocumentListener(project))
        }
    }

    //TODO error handling
    private fun findDocument(fileRelativePath: String, project: Project): Document {
        val vFile = project.baseDir.findFileByRelativePath(fileRelativePath)!!
        val psiFile = PsiManager.getInstance(project).findFile(vFile)!!
        return PsiDocumentManager.getInstance(project).getDocument(psiFile)!!
    }
}
