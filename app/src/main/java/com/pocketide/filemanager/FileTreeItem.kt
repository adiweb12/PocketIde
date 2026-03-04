package com.pocketide.filemanager

import java.io.File

data class FileTreeItem(
    val file: File,
    val depth: Int,
    val isDirectory: Boolean,
    var isExpanded: Boolean = true
)
