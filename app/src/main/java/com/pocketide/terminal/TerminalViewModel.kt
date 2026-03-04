package com.pocketide.terminal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TerminalViewModel : ViewModel() {

    private val _output = MutableLiveData<String>("PocketIDE Terminal\n$ ")
    val output: LiveData<String> = _output
    private val history = StringBuilder("PocketIDE Terminal\n")

    fun runCommand(command: String) {
        history.append("$ $command\n")
        viewModelScope.launch(Dispatchers.IO) {
            val result = ShellExecutor.execute(command)
            history.append("$result\n")
            _output.postValue(history.toString())
        }
    }

    fun clearOutput() {
        history.clear()
        history.append("PocketIDE Terminal\n")
        _output.value = history.toString()
    }
}
