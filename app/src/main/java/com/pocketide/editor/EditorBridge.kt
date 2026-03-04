package com.pocketide.editor

import android.webkit.JavascriptInterface

class AndroidEditorBridge(private val monacoView: MonacoWebView) {

    @JavascriptInterface
    fun onContentChanged(content: String) {
        monacoView.post {
            monacoView.notifyContentChanged(content)
        }
    }

    @JavascriptInterface
    fun onCursorMoved(line: Int, column: Int) {
        monacoView.post {
            monacoView.notifyCursorMoved(line, column)
        }
    }

    @JavascriptInterface
    fun requestSuggestions(prefix: String, language: String) {
        monacoView.post {
            monacoView.notifySuggestionRequested(prefix, language)
        }
    }

    @JavascriptInterface
    fun log(message: String) {
        android.util.Log.d("MonacoEditor", message)
    }
}
