package org.koiki.beepro.dancer.intellij.listener

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager

class MyFileEditorManagerListener(
        private val project: Project
) : FileEditorManagerListener {
    private val log = Logger.getInstance(this::class.java)

    // this works when file opens so this does not work for opened file
    override fun fileOpened(source: FileEditorManager, vFile: VirtualFile) {
        //super.fileOpened(source, file)
    }

    override fun fileClosed(source: FileEditorManager, vFile: VirtualFile) {
        log.info("fileClosed")
        //super.fileClosed(source, file)
    }

    override fun selectionChanged(event: FileEditorManagerEvent) {
        log.info("a ${event.newFile} is selected")

        // TODO to display an error message on UI if a file or a document is null
        val psiFile: PsiFile = PsiManager.getInstance(project).findFile(event.newFile!!)!!
        val document: Document = PsiDocumentManager.getInstance(project).getDocument(psiFile)!!

        // TODO to prevent adding listener twice if a same file is selected again
        //document.removeDocumentListener(MyDocumentListenerFactory.getInstance())
        document.addDocumentListener(MyDocumentListener(project))
    }
}
