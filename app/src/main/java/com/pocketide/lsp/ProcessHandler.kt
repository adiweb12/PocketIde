package com.pocketide.lsp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProcessHandler {
    fun streamOutput(process: Process): Flow<String> = flow {
        val reader = process.inputStream.bufferedReader()
        var line: String?
        while (reader.readLine().also { line = it } != null) emit(line!!)
    }.flowOn(Dispatchers.IO)
    fun isAlive(process: Process): Boolean = process.isAlive
    fun kill(process: Process) = process.destroy()
}
