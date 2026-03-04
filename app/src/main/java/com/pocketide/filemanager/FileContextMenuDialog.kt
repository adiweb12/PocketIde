package com.pocketide.filemanager

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.io.File

class FileContextMenuDialog(
    private val file: File,
    private val onRename: (String) -> Unit,
    private val onDelete: () -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val options = arrayOf("Rename", "Delete")
        return AlertDialog.Builder(requireContext())
            .setTitle(file.name)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showRenameDialog()
                    1 -> { onDelete(); dismiss() }
                }
            }.create()
    }

    private fun showRenameDialog() {
        val input = EditText(requireContext()).apply { setText(file.name) }
        AlertDialog.Builder(requireContext())
            .setTitle("Rename")
            .setView(input)
            .setPositiveButton("Rename") { _, _ -> onRename(input.text.toString()) }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
