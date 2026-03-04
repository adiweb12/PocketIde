package com.pocketide.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.pocketide.R
import com.pocketide.databinding.ActivityMainBinding
import com.pocketide.editor.EditorFragment
import com.pocketide.filemanager.FileExplorerFragment
import com.pocketide.settings.SettingsFragment
import com.pocketide.terminal.TerminalFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            showEditor()
            showFileExplorer()
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            handleBottomNav(item)
            true
        }

        binding.fabNewFile.setOnClickListener {
            showNewFileDialog()
        }
    }

    private fun showEditor() {
        supportFragmentManager.commit {
            replace(R.id.editorContainer, EditorFragment())
        }
    }

    private fun showFileExplorer() {
        supportFragmentManager.commit {
            replace(R.id.fileExplorerContainer, FileExplorerFragment())
        }
    }

    private fun handleBottomNav(item: MenuItem) {
        when (item.itemId) {
            R.id.nav_files -> showFileExplorer()
            R.id.nav_editor -> showEditor()
            R.id.nav_terminal -> showTerminal()
            R.id.nav_settings -> showSettings()
        }
    }

    private fun showTerminal() {
        supportFragmentManager.commit {
            replace(R.id.editorContainer, TerminalFragment())
            addToBackStack(null)
        }
    }

    private fun showSettings() {
        supportFragmentManager.commit {
            replace(R.id.editorContainer, SettingsFragment())
            addToBackStack(null)
        }
    }

    private fun showNewFileDialog() {
        NewFileDialogFragment().show(supportFragmentManager, "new_file")
    }
}
