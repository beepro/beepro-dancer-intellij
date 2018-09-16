package org.koiki.beepro.dancer.intellij.ui

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import org.koiki.beepro.dancer.intellij.dance.DanceOperation
import java.awt.Color
import java.awt.Font
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.util.containers.stream
import org.koiki.beepro.dancer.intellij.listener.MyFileEditorManagerListener
import org.koiki.beepro.dancer.intellij.dance.DanceOperationFactory
import org.koiki.beepro.dancer.intellij.ui.button.ConnectButtonListener
import org.koiki.beepro.dancer.intellij.ui.button.DisconnectButtonListener


class MainToolWindowFactory : ToolWindowFactory {
    private val log = Logger.getInstance(this::class.java)

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        log.info("project.workspaceFile: ${project.workspaceFile}")

        // fetching all opened files when idea starts up
        FileEditorManager.getInstance(project).openFiles.stream().forEach {file -> log.info("openedFile: ${file}")}

        val messageBus = project.messageBus
        messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, MyFileEditorManagerListener(project))

        val danceOperation = DanceOperationFactory
                .init(project)
                .getInstance()
        val bus = messageBus
        bus.connect().subscribe(DanceOperation.CONNECTED_TOPIC, danceOperation)

        val label = JLabel("Hello World!")

        val textField = JTextField(8)
        textField.setFont(Font("ＭＳ ゴシック", Font.BOLD, 16))

        val button = JButton("connect")
        button.addActionListener(ConnectButtonListener(textField, project))

        val closeButton = JButton("close")
        closeButton.addActionListener(DisconnectButtonListener(danceOperation))

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
