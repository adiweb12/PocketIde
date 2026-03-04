package com.pocketide.data.models

import java.io.File

data class FileNode(
    val name: String,
    val path: String,
    val isDirectory: Boolean,
    val children: MutableList<FileNode> = mutableListOf(),
    var isExpanded: Boolean = false
) {
    val file: File get() = File(path)
    val extension: String get() = name.substringAfterLast('.', "")
    val size: Long get() = if (!isDirectory) file.length() else 0L
}

data class EditorTab(
    val fileName: String,
    val filePath: String,
    val content: String,
    val language: String,
    val isModified: Boolean = false
)
