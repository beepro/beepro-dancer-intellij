package org.koiki.beepro.dancer.intellij.listener

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.openapi.editor.actionSystem.EditorActionManager

class TypedEventIllustration : AnAction() {
    companion object {
        init {
            val actionManager = EditorActionManager.getInstance()
            val typedAction = actionManager.typedAction
            typedAction.setupHandler(TypedEventHandler())
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val actionManager = EditorActionManager.getInstance()
        val actionHandler = actionManager.getActionHandler(IdeActions.ACTION_EDITOR_CLONE_CARET_BELOW)
        actionHandler.execute(editor, editor.caretModel.currentCaret, e.dataContext)
    }
}