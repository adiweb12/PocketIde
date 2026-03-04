package com.pocketide.terminal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ShellExecutor {

    private val allowedCommands = setOf(
        "ls", "pwd", "echo", "cat", "mkdir", "rm", "mv", "cp",
        "python3", "node", "dart", "java", "javac", "grep", "find",
        "chmod", "touch", "head", "tail", "wc", "date", "whoami"
    )

    suspend fun execute(command: String): String = withContext(Dispatchers.IO) {
        val parts = command.trim().split(" ")
        val base = parts.firstOrNull() ?: return@withContext "Error: empty command"

        if (base !in allowedCommands) {
            return@withContext "Command not allowed: $base"
        }

        try {
            val process = ProcessBuilder(parts)
                .redirectErrorStream(true)
                .start()
            val output = process.inputStream.bufferedReader().readText()
            process.waitFor()
            output.ifEmpty { "(no output)" }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}
