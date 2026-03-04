package com.pocketide.data.repository

import android.content.Context
import androidx.documentfile.provider.DocumentFile
import com.pocketide.data.models.FileModel
import java.io.File

class FileRepository(private val context: Context) {

    fun readFile(path: String): FileModel? {
        return try {
            val file = File(path)
            if (!file.exists()) return null
            val content = file.readText()
            FileModel(file.name, content, detectLang(file.name), path)
        } catch (e: Exception) { null }
    }

    fun writeFile(path: String, content: String): Boolean {
        return try {
            File(path).writeText(content)
            true
        } catch (e: Exception) { false }
    }

    fun createFile(dirPath: String, name: String): Boolean {
        return try {
            File(dirPath, name).createNewFile()
        } catch (e: Exception) { false }
    }

    fun deleteFile(path: String): Boolean = File(path).deleteRecursively()

    fun renameFile(path: String, newName: String): Boolean {
        val file = File(path)
        return file.renameTo(File(file.parent, newName))
    }

    fun listFiles(dirPath: String): List<File> {
        return File(dirPath).listFiles()?.toList() ?: emptyList()
    }

    private fun detectLang(name: String): String = when {
        name.endsWith(".kt") -> "kotlin"
        name.endsWith(".py") -> "python"
        name.endsWith(".js") -> "javascript"
        name.endsWith(".html") -> "html"
        name.endsWith(".css") -> "css"
        name.endsWith(".java") -> "java"
        name.endsWith(".cpp") -> "cpp"
        name.endsWith(".dart") -> "dart"
        else -> "plaintext"
    }
}
