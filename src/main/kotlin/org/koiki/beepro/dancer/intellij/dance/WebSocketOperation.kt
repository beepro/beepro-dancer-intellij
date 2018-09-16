package org.koiki.beepro.dancer.intellij.dance

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiManager
import com.intellij.util.concurrency.AppExecutorUtil
import org.glassfish.tyrus.client.ClientManager
import org.koiki.beepro.dancer.intellij.dance.message.JoinMessage
import org.koiki.beepro.dancer.intellij.dance.message.Message
import org.koiki.beepro.dancer.intellij.dance.message.User
import org.koiki.beepro.dancer.intellij.listener.MyDocumentListenerFactory
import java.net.URI
import java.util.concurrent.TimeUnit
import javax.websocket.*

@ClientEndpoint
class WebSocketOperation(
        private val project: Project
) : DanceOperation {

    private val log = Logger.getInstance(this::class.java)

    private val objectMapper = ObjectMapper().registerModule(KotlinModule())

    private var session: Session? = null

    override fun connect(uri: URI) {
        log.info("I got notification by clicking button!, value: $uri")

        ClientManager.createClient().connectToServer(this, uri)

        sendMessage(JoinMessage(user = User(id = "ninja", icon = "hoge.png")))

        AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(
                {
                    this.keepAlive()
                }, 1, 5, TimeUnit.SECONDS)
    }

    override fun sendMessage(message: Message) {
        log.info("send message: ${objectMapper.writeValueAsString(message)}")

        session?.asyncRemote?.sendObject(objectMapper.writeValueAsString(message))
    }

    private fun keepAlive() {
        log.info("sending keepalive")
        session?.asyncRemote?.sendObject("KEEPALIVE")
    }

    override fun close() {
        session?.close()
        log.info("session closed")
    }

    @OnOpen
    fun onOpen(session: Session) {
        log.info("WebSocket connection is established, session: $session")
        this.session = session
    }

    @OnMessage
    override fun onMessage(message: String) {
        log.info("message got, ${message}")

        project.baseDir
        project.basePath
        project.projectFile
        project.projectFilePath

        log.info("project.baseDir: ${project.baseDir}" +
                " project.basePath: ${project.basePath}" +
                " project.projectFile: ${project.projectFile}" +
                " project.projectFilePath: ${project.projectFilePath}")

        // baseDir: "file://${project_home}"
        val vFile = project.baseDir.findFileByRelativePath("src/main/kotlin/org/koiki/beepro/dancer/intellij/test/test.txt")
        log.info("this is updated target vFile: ${vFile}")

        if (vFile != null) {
            WriteCommandAction.runWriteCommandAction(project, Runnable {
                val psiFile = PsiManager.getInstance(project).findFile(vFile)
                log.info("update target psiFile: ${psiFile}")
                if (psiFile != null) {
                    val targetDocument = PsiDocumentManager.getInstance(project).getDocument(psiFile)

                    targetDocument?.removeDocumentListener(MyDocumentListenerFactory.getInstance())

                    // insertString() overwrites text so replaceString is fine
                    targetDocument?.replaceString(0, 0, "inserted by dancer\n")

                    targetDocument?.addDocumentListener(MyDocumentListenerFactory.getInstance())
                    log.info("document updated by dancer")
                }
            });
        }
    }

    @OnClose
    fun onClose(session: Session, closeReason: CloseReason) {
        log.info("closed")
    }
}