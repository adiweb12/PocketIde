package com.pocketide.lsp

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LSPManager(private val context: Context) {
    private val processes = mutableMapOf<String, Process>()

    suspend fun startServer(language: String): Boolean = withContext(Dispatchers.IO) {
        if (processes.containsKey(language)) return@withContext true
        val cmd = getServerCommand(language) ?: return@withContext false
        try { processes[language] = ProcessBuilder(cmd).redirectErrorStream(true).start(); true }
        catch (e: Exception) { false }
    }

    suspend fun sendRequest(language: String, jsonRpc: String): String = withContext(Dispatchers.IO) {
        val process = processes[language] ?: return@withContext ""
        try {
            process.outputStream.bufferedWriter().apply { write("Content-Length: ${jsonRpc.length}\r\n\r\n$jsonRpc"); flush() }
            process.inputStream.bufferedReader().readLine() ?: ""
        } catch (e: Exception) { "" }
    }

    fun stopAll() { processes.values.forEach { it.destroy() }; processes.clear() }

    private fun getServerCommand(language: String): List<String>? = when (language) {
        "python" -> listOf("pyls")
        "dart" -> listOf("dart", "language-server", "--client-id", "pocketide")
        else -> null
    }
}
