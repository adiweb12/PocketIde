package com.pocketide.domain.language

class LanguageDetector {

    fun detect(filename: String): String {
        val ext = filename.substringAfterLast('.', "").lowercase()
        return extensionMap[ext] ?: "plaintext"
    }

    fun getMonacoLanguage(language: String): String {
        return monacoMap[language] ?: language
    }

    fun getDisplayName(language: String): String {
        return displayNames[language] ?: language.replaceFirstChar { it.uppercase() }
    }

    companion object {
        val extensionMap = mapOf(
            "py" to "python",
            "js" to "javascript",
            "ts" to "typescript",
            "jsx" to "javascript",
            "tsx" to "typescript",
            "kt" to "kotlin",
            "kts" to "kotlin",
            "java" to "java",
            "html" to "html",
            "htm" to "html",
            "css" to "css",
            "scss" to "scss",
            "sass" to "scss",
            "dart" to "dart",
            "json" to "json",
            "xml" to "xml",
            "yaml" to "yaml",
            "yml" to "yaml",
            "toml" to "ini",
            "md" to "markdown",
            "sh" to "shell",
            "bash" to "shell",
            "c" to "c",
            "cpp" to "cpp",
            "h" to "c",
            "hpp" to "cpp",
            "go" to "go",
            "rs" to "rust",
            "rb" to "ruby",
            "php" to "php",
            "sql" to "sql",
            "gradle" to "groovy",
            "groovy" to "groovy",
            "txt" to "plaintext",
            "log" to "plaintext"
        )

        val monacoMap = mapOf(
            "python" to "python",
            "javascript" to "javascript",
            "typescript" to "typescript",
            "kotlin" to "kotlin",
            "java" to "java",
            "html" to "html",
            "css" to "css",
            "dart" to "dart",
            "shell" to "shell",
            "markdown" to "markdown"
        )

        val displayNames = mapOf(
            "python" to "Python",
            "javascript" to "JavaScript",
            "typescript" to "TypeScript",
            "kotlin" to "Kotlin",
            "java" to "Java",
            "html" to "HTML",
            "css" to "CSS",
            "dart" to "Dart",
            "shell" to "Shell",
            "markdown" to "Markdown",
            "plaintext" to "Plain Text"
        )
    }
}
