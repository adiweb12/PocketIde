package com.pocketide.data.models

data class ProjectModel(
    val name: String,
    val rootPath: String,
    val createdAt: Long = System.currentTimeMillis()
)
