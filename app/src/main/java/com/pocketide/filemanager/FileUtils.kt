package com.pocketide.filemanager

import java.io.File

object FileUtils {
    fun getExtension(name: String): String = name.substringAfterLast(".", "")
    fun isTextFile(file: File): Boolean {
        val ext = getExtension(file.name)
        return ext in listOf("kt", "py", "js", "ts", "html", "css", "java", "cpp", "dart", "json", "xml", "md", "txt")
    }
    fun formatSize(bytes: Long): String = when {
        bytes < 1024 -> "$bytes B"
        bytes < 1048576 -> "${bytes / 1024} KB"
        else -> "${bytes / 1048576} MB"
    }
}
