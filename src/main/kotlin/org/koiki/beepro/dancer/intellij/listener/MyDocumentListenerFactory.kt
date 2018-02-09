package org.koiki.beepro.dancer.intellij.listener

object MyDocumentListenerFactory {
    private val myDocumentListener = MyDocumentListener()

    fun getInstance(): MyDocumentListener {
        return this.myDocumentListener
    }
}