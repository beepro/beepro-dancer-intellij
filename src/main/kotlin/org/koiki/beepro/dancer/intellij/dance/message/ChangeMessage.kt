package org.koiki.beepro.dancer.intellij.dance.message

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

data class ChangeMessage(
        override val type: MessageType = MessageType.change,
        val path: String,
        val change: Change
) : Message (
        type = type
) {

    companion object {
        fun create(
                project: Project,
                vFile: VirtualFile,
                event: DocumentEvent
        ): ChangeMessage =
                ChangeMessage(
                        path = vFile.path.replace(oldValue = project.basePath.toString() + "/", newValue = ""),
                        change = Change.create(event)
                )
    }
}
