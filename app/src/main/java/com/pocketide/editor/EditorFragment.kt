package com.pocketide.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pocketide.databinding.FragmentEditorBinding

class EditorFragment : Fragment() {

    private var _binding: FragmentEditorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditorViewModel by viewModels()
    private lateinit var monacoWebView: MonacoWebView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        monacoWebView = MonacoWebView(requireContext())
        binding.editorWebViewContainer.addView(monacoWebView)
        monacoWebView.initialize()

        viewModel.currentFile.observe(viewLifecycleOwner) { file ->
            file?.let {
                monacoWebView.loadFile(it.name, it.content, it.language)
            }
        }

        viewModel.suggestions.observe(viewLifecycleOwner) { suggestions ->
            monacoWebView.showSuggestions(suggestions)
        }

        monacoWebView.setOnContentChangedListener { content ->
            viewModel.onContentChanged(content)
        }

        monacoWebView.setOnCursorChangedListener { line, col ->
            viewModel.onCursorMoved(line, col)
        }

        binding.tabLayout.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                tab?.tag?.let { viewModel.openFile(it as String) }
            }
            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}
            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}
        })

        binding.btnSave.setOnClickListener { viewModel.saveCurrentFile() }
        binding.btnRun.setOnClickListener { viewModel.runCurrentFile() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
