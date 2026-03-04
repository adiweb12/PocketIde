package com.pocketide.domain.usecase

import com.pocketide.domain.language.KotlinSuggestions
import com.pocketide.domain.language.PythonSuggestions
import com.pocketide.domain.language.JavaScriptSuggestions

class GetSuggestionsUseCase {
    suspend fun execute(code: String, language: String): List<String> {
        val lastWord = code.trimEnd().split(Regex("\\s+|[.({,]")).lastOrNull() ?: ""
        val keywords = when (language) {
            "kotlin" -> KotlinSuggestions.keywords
            "python" -> PythonSuggestions.keywords
            "javascript", "typescript" -> JavaScriptSuggestions.keywords
            else -> emptyList()
        }
        return keywords.filter { it.startsWith(lastWord) && lastWord.isNotEmpty() }.take(10)
    }
}
