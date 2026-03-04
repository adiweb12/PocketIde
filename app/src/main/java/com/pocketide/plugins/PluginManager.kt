package com.pocketide.plugins

import android.content.Context
import com.google.gson.Gson
import com.pocketide.data.models.PluginModel
import java.io.File

class PluginManager(private val context: Context) {

    private val plugins = mutableListOf<PluginModel>()
    private val gson = Gson()

    fun loadPlugins() {
        val pluginDir = File(context.filesDir, "plugins")
        pluginDir.mkdirs()
        plugins.clear()
        pluginDir.listFiles { f -> f.isDirectory }?.forEach { dir ->
            val manifest = File(dir, "plugin_manifest.json")
            if (manifest.exists()) {
                try {
                    val model = gson.fromJson(manifest.readText(), PluginModel::class.java)
                    plugins.add(model)
                } catch (e: Exception) { /* skip malformed plugin */ }
            }
        }
    }

    fun getPluginsForLanguage(lang: String): List<PluginModel> =
        plugins.filter { it.language == lang && it.enabled }

    fun enablePlugin(name: String) {
        val idx = plugins.indexOfFirst { it.name == name }
        if (idx >= 0) plugins[idx] = plugins[idx].copy(enabled = true)
    }

    fun disablePlugin(name: String) {
        val idx = plugins.indexOfFirst { it.name == name }
        if (idx >= 0) plugins[idx] = plugins[idx].copy(enabled = false)
    }

    fun getAllPlugins(): List<PluginModel> = plugins.toList()
}
