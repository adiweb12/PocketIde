package com.pocketide.domain.usecase

import com.pocketide.data.models.FileModel
import java.io.File

class SaveFileUseCase {
    suspend fun execute(file: FileModel): Boolean {
        return try { File(file.path).writeText(file.content); true }
        catch (e: Exception) { false }
    }
}
