package org.koiki.beepro.dancer.intellij.dance

import com.intellij.openapi.project.Project

object DanceOperationFactory {
    private var danceOperations: DanceOperations?= null

    fun getInstance(): DanceOperations {
        val webSocketClient = this.danceOperations

        if (webSocketClient != null) {
            return webSocketClient
        } else {
            throw RuntimeException("WebSocketOperations is not initialized")
        }
    }

    fun init(project: Project): DanceOperationFactory {
        this.danceOperations = WebSocketOperations(project)
        return this
    }
}