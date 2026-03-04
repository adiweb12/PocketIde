package com.pocketide.data.models

data class PluginModel(
    val name: String,
    val version: String,
    val language: String,
    val snippetsPath: String,
    val enabled: Boolean = true
)
