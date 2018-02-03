package org.koiki.beepro.dancer.intellij.ui

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import org.koiki.beepro.dancer.intellij.websocket.WebSocketImpl
import org.koiki.beepro.dancer.intellij.websocket.WebSocketInterface
import java.awt.Color
import java.awt.Font
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import javax.xml.datatype.DatatypeConstants.SECONDS
import com.intellij.util.concurrency.AppExecutorUtil
import java.util.concurrent.TimeUnit
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiManager
import com.sun.javafx.scene.CameraHelper.project
import com.intellij.util.messages.MessageBus


class MainToolWindowFactory : ToolWindowFactory {
    private val log = Logger.getInstance(this::class.java)

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        log.info("factory starts")

        val messageBus = project.messageBus
        messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, object : FileEditorManagerListener {
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

                        document?.addDocumentListener(object : DocumentListener {
                            override fun beforeDocumentChange(event: DocumentEvent) {
                                log.info("beforeDocumentChange, event: ${event}")
                            }

                            override fun documentChanged(event: DocumentEvent) {
                                log.info("documentChanged")
                            }
                        })

                    } else {
                        log.info("psi file was not found")
                    }
                } else {
                    log.info("vFile not found")
                }
                //super.selectionChanged(event)
            }
        })

        val webSocketImpl = WebSocketImpl()
        val bus = project.messageBus
        bus.connect().subscribe(WebSocketInterface.CONNECTED_TOPIC, webSocketImpl)

        val label = JLabel("Hello World!")

        val textField = JTextField(8)
        textField.setFont(Font("ＭＳ ゴシック", Font.BOLD, 16))

        val button = JButton("connect")
        button.addActionListener(ConnectButtonListener(textField, project))

        val closeButton = JButton("close")
        closeButton.addActionListener({
            webSocketImpl.close()
        })

        val jPanel = JPanel()
        jPanel.add(label)
        jPanel.setBackground(Color.RED)
        jPanel.add(textField)
        jPanel.add(button)
        jPanel.add(closeButton)

        val contentManager = toolWindow.contentManager
        val content = contentManager.factory.createContent(jPanel, null, false)

        contentManager.addContent(content)
    }
}
