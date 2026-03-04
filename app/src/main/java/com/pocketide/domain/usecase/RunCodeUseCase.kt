package com.pocketide.domain.usecase

import com.pocketide.data.models.FileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RunCodeUseCase {
    suspend fun execute(file: FileModel): String = withContext(Dispatchers.IO) {
        when (file.language) {
            "python" -> runProcess("python3", file.path)
            "javascript" -> runProcess("node", file.path)
            "html" -> "HTML Preview mode - open in WebView"
            else -> "Execution not supported for ${file.language} yet."
        }
    }
    private fun runProcess(vararg cmd: String): String {
        return try {
            val p = ProcessBuilder(*cmd).redirectErrorStream(true).start()
            val out = p.inputStream.bufferedReader().readText()
            p.waitFor()
            out.ifEmpty { "(no output)" }
        } catch (e: Exception) { "Runtime error: ${e.message}" }
    }
}
