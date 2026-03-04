package com.pocketide.filemanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pocketide.data.repository.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class FileExplorerViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = FileRepository(application)
    private val _fileTree = MutableLiveData<List<FileTreeItem>>()
    val fileTree: LiveData<List<FileTreeItem>> = _fileTree

    fun loadDefaultProject() {
        viewModelScope.launch(Dispatchers.IO) {
            val root = File(getApplication<Application>().filesDir, "projects")
            root.mkdirs()
            _fileTree.postValue(buildFileTree(root, 0))
        }
    }

    fun loadPath(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _fileTree.postValue(buildFileTree(File(path), 0))
        }
    }

    fun openFile(file: File) {
        // Notify editor via shared ViewModel or event bus
    }

    fun renameFile(file: File, newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.renameFile(file.absolutePath, newName)
            loadDefaultProject()
        }
    }

    fun deleteFile(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteFile(file.absolutePath)
            loadDefaultProject()
        }
    }

    private fun buildFileTree(dir: File, depth: Int): List<FileTreeItem> {
        val items = mutableListOf<FileTreeItem>()
        dir.listFiles()?.sortedWith(compareBy({ !it.isDirectory }, { it.name }))?.forEach { f ->
            items.add(FileTreeItem(f, depth, f.isDirectory))
            if (f.isDirectory) items.addAll(buildFileTree(f, depth + 1))
        }
        return items
    }
}
