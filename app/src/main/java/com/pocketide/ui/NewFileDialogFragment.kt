package com.pocketide.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class NewFileDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val input = EditText(requireContext()).apply {
            hint = "filename.kt"
        }
        return AlertDialog.Builder(requireContext())
            .setTitle("New File")
            .setView(input)
            .setPositiveButton("Create") { _, _ ->
                val name = input.text.toString().trim()
                if (name.isNotEmpty()) {
                    (activity as? MainActivity)?.let {
                        // Trigger file creation via ViewModel / shared flow
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
