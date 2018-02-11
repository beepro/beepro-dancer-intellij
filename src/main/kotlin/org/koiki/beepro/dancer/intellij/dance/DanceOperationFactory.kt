package org.koiki.beepro.dancer.intellij.dance

import com.intellij.openapi.project.Project

object DanceOperationFactory {
    private var danceOperation: DanceOperation?= null

    fun getInstance(): DanceOperation {
        val webSocketClient = this.danceOperation

        if (webSocketClient != null) {
            return webSocketClient
        } else {
            throw RuntimeException("WebSocketOperation is not initialized")
        }
    }

    fun init(project: Project): DanceOperationFactory {
        this.danceOperation = WebSocketOperation(project)
        return this
    }
}