package com.pocketide.data.models

data class FileModel(
    val name: String,
    val content: String,
    val language: String,
    val path: String,
    val isDirty: Boolean = false
)
