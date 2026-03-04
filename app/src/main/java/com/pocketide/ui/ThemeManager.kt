package com.pocketide.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {

    private const val PREF_NAME = "theme_prefs"
    private const val KEY_THEME = "editor_theme"
    private const val KEY_DARK_MODE = "dark_mode"

    fun applyDarkMode(isDark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun saveTheme(context: Context, theme: String) {
        getPrefs(context).edit().putString(KEY_THEME, theme).apply()
    }

    fun getTheme(context: Context): String =
        getPrefs(context).getString(KEY_THEME, "vs-dark") ?: "vs-dark"

    fun saveDarkMode(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_DARK_MODE, enabled).apply()
    }

    fun isDarkMode(context: Context): Boolean =
        getPrefs(context).getBoolean(KEY_DARK_MODE, true)

    private fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
}
