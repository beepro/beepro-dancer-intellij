package org.koiki.beepro.dancer.intellij.listener

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

/**
 * see details
 * https://intellij-support.jetbrains.com/hc/en-us/community/posts/206756295-TypedActionHandler-with-default-typing-behavior
 */
class TypedEventHandlerDelegate : TypedHandlerDelegate() {
    override fun charTyped(c: Char, project: Project, editor: Editor, file: PsiFile): Result {
        return Result.CONTINUE
    }
}