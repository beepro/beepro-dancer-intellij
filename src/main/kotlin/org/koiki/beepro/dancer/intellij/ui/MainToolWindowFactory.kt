package org.koiki.beepro.dancer.intellij.ui

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import java.awt.Color
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class MainToolWindowFactory : ToolWindowFactory {
    private val log = Logger.getInstance(this::class.java)

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        log.info("factory starts")

        val label = JLabel("Hello World!")

        val textField = JTextField(8)
        textField.setFont(Font("ＭＳ ゴシック", Font.BOLD, 16))

        val button = JButton()
        button.addActionListener(ConnectButtonListene(textField))

        val jPanel = JPanel()
        jPanel.add(label)
        jPanel.setBackground(Color.RED)
        jPanel.add(textField)
        jPanel.add(button)

        val contentManager = toolWindow.contentManager
        val content = contentManager.factory.createContent(jPanel, null, false)

        contentManager.addContent(content)
    }
}
