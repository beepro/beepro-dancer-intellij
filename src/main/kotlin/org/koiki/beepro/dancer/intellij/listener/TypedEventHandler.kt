package org.koiki.beepro.dancer.intellij.listener

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.TypedActionHandler
import org.bouncycastle.asn1.x500.style.RFC4519Style.c
import com.intellij.openapi.util.JDOMBuilder.document
import com.intellij.openapi.editor.CaretModel

class TypedEventHandler : TypedActionHandler {
    private val log = Logger.getInstance(this::class.java)

    override fun execute(editor: Editor, charTyped: Char, dataContext: DataContext) {
        log.info("typed: $charTyped")

        val document = editor.document
        val project = editor.project
        val runnable = Runnable {
            val caretModel = editor.caretModel
            document.insertString(caretModel.offset, charTyped.toString())
            caretModel.moveToOffset(caretModel.offset + 1)
        }
        WriteCommandAction.runWriteCommandAction(project, runnable)
    }
}