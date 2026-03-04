package com.pocketide.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocketide.data.models.FileModel
import com.pocketide.domain.usecase.GetSuggestionsUseCase
import com.pocketide.domain.usecase.SaveFileUseCase
import com.pocketide.domain.usecase.RunCodeUseCase
import kotlinx.coroutines.launch

class EditorViewModel : ViewModel() {

    private val getSuggestionsUseCase = GetSuggestionsUseCase()
    private val saveFileUseCase = SaveFileUseCase()
    private val runCodeUseCase = RunCodeUseCase()

    private val _currentFile = MutableLiveData<FileModel?>()
    val currentFile: LiveData<FileModel?> = _currentFile

    private val _suggestions = MutableLiveData<List<String>>()
    val suggestions: LiveData<List<String>> = _suggestions

    private val _runOutput = MutableLiveData<String>()
    val runOutput: LiveData<String> = _runOutput

    private val _openTabs = MutableLiveData<List<String>>(emptyList())
    val openTabs: LiveData<List<String>> = _openTabs

    fun openFile(path: String) {
        viewModelScope.launch {
            val content = java.io.File(path).readText()
            val lang = detectLanguage(path)
            _currentFile.value = FileModel(path.substringAfterLast("/"), content, lang, path)
            addTab(path)
        }
    }

    fun onContentChanged(content: String) {
        _currentFile.value = _currentFile.value?.copy(content = content, isDirty = true)
        triggerAutocomplete(content)
    }

    fun onCursorMoved(line: Int, col: Int) {
        // LSP hover / diagnostics can be triggered here
    }

    fun saveCurrentFile() {
        viewModelScope.launch {
            _currentFile.value?.let { file ->
                saveFileUseCase.execute(file)
                _currentFile.value = file.copy(isDirty = false)
            }
        }
    }

    fun runCurrentFile() {
        viewModelScope.launch {
            _currentFile.value?.let { file ->
                val output = runCodeUseCase.execute(file)
                _runOutput.value = output
            }
        }
    }

    private fun triggerAutocomplete(content: String) {
        viewModelScope.launch {
            val lang = _currentFile.value?.language ?: return@launch
            val sugg = getSuggestionsUseCase.execute(content, lang)
            _suggestions.value = sugg
        }
    }

    private fun addTab(path: String) {
        val current = _openTabs.value?.toMutableList() ?: mutableListOf()
        if (!current.contains(path)) current.add(path)
        _openTabs.value = current
    }

    private fun detectLanguage(path: String): String = when {
        path.endsWith(".kt") -> "kotlin"
        path.endsWith(".py") -> "python"
        path.endsWith(".js") -> "javascript"
        path.endsWith(".ts") -> "typescript"
        path.endsWith(".html") -> "html"
        path.endsWith(".css") -> "css"
        path.endsWith(".java") -> "java"
        path.endsWith(".cpp") || path.endsWith(".cc") -> "cpp"
        path.endsWith(".dart") -> "dart"
        path.endsWith(".json") -> "json"
        path.endsWith(".xml") -> "xml"
        else -> "plaintext"
    }
}
