package com.pocketide.plugins

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class PluginLoader(private val context: Context) {

    private val gson = Gson()

    fun loadSnippets(snippetsPath: String): Map<String, String> {
        return try {
            val file = File(context.filesDir, snippetsPath)
            if (!file.exists()) return emptyMap()
            val type = object : TypeToken<Map<String, String>>() {}.type
            gson.fromJson(file.readText(), type) ?: emptyMap()
        } catch (e: Exception) { emptyMap() }
    }

    fun getSnippetKeys(snippetsPath: String): List<String> =
        loadSnippets(snippetsPath).keys.toList()
}
