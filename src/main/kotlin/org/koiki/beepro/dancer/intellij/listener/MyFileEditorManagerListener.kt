package org.koiki.beepro.dancer.intellij.listener

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiManager

class MyFileEditorManagerListener : FileEditorManagerListener {
    private val log = Logger.getInstance(this::class.java)
    private val project: Project

    constructor(project: Project) {
        this.project = project
    }

    // this works when file opens so this does not work for opened file
    override fun fileOpened(source: FileEditorManager, vFile: VirtualFile) {
        //super.fileOpened(source, file)
    }

    override fun fileClosed(source: FileEditorManager, vFile: VirtualFile) {
        log.info("fileClosed")
        //super.fileClosed(source, file)
    }

    override fun selectionChanged(event: FileEditorManagerEvent) {
        log.info("selectionChanged")

        val vFile = event.newFile

        if (vFile != null) {
            log.info("vFile: ${vFile}")
            val psiFile = PsiManager.getInstance(project).findFile(vFile)
            if (psiFile != null) {
                log.info("psiFile found, ${psiFile}")
                val document = PsiDocumentManager.getInstance(project).getDocument(psiFile)

                if (document == null)
                    log.info("document is null")

                log.info("document found, ${document}")

                //TODO this should be improved
                document?.removeDocumentListener(MyDocumentListenerFactory.getInstance())
                document?.addDocumentListener(MyDocumentListenerFactory.getInstance())

            } else {
                log.info("psi file was not found")
            }
        } else {
            log.info("vFile not found")
        }
        //super.selectionChanged(event)
    }
}