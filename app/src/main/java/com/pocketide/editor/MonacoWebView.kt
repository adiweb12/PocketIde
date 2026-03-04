package com.pocketide.editor

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

@SuppressLint("SetJavaScriptEnabled")
class MonacoWebView(context: Context) : WebView(context) {

    private var onContentChanged: ((String) -> Unit)? = null
    private var onCursorChanged: ((Int, Int) -> Unit)? = null

    init {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        webViewClient = WebViewClient()
        webChromeClient = WebChromeClient()
        addJavascriptInterface(EditorJSBridge(), "KotlinBridge")
    }

    fun initialize() {
        loadUrl("file:///android_asset/monaco/index.html")
    }

    fun loadFile(fileName: String, content: String, language: String) {
        val escaped = content.replace("\\", "\\\\").replace("`", "\\`").replace("$", "\\$")
        evaluateJavascript("""
            window.editorSetContent(`$escaped`, '$language');
        """.trimIndent(), null)
    }

    fun showSuggestions(suggestions: List<String>) {
        val json = suggestions.joinToString(",") { "\"$it\"" }
        evaluateJavascript("window.editorSetSuggestions([$json]);", null)
    }

    fun setTheme(theme: String) {
        evaluateJavascript("window.editorSetTheme('$theme');", null)
    }

    fun setOnContentChangedListener(listener: (String) -> Unit) {
        onContentChanged = listener
    }

    fun setOnCursorChangedListener(listener: (Int, Int) -> Unit) {
        onCursorChanged = listener
    }

    inner class EditorJSBridge {
        @JavascriptInterface
        fun onContentChanged(content: String) {
            post { onContentChanged?.invoke(content) }
        }

        @JavascriptInterface
        fun onCursorChanged(line: Int, col: Int) {
            post { onCursorChanged?.invoke(line, col) }
        }

        @JavascriptInterface
        fun onSaveRequested() {
            post { /* trigger save */ }
        }
    }
}
