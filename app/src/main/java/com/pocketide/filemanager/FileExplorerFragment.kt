package com.pocketide.filemanager

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pocketide.databinding.FragmentFileExplorerBinding

class FileExplorerFragment : Fragment() {

    private var _binding: FragmentFileExplorerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FileExplorerViewModel by viewModels()
    private lateinit var adapter: FileTreeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFileExplorerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FileTreeAdapter(
            onFileClick = { file -> viewModel.openFile(file) },
            onFileLongClick = { file -> showContextMenu(file) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.fileTree.observe(viewLifecycleOwner) { files ->
            adapter.submitList(files)
        }
        viewModel.loadDefaultProject()
    }

    private fun showContextMenu(file: java.io.File) {
        FileContextMenuDialog(file,
            onRename = { viewModel.renameFile(file, it) },
            onDelete = { viewModel.deleteFile(file) }
        ).show(childFragmentManager, "ctx_menu")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
