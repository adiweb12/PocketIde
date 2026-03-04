package com.pocketide.filemanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pocketide.databinding.ItemFileTreeBinding

class FileTreeAdapter(
    private val onFileClick: (java.io.File) -> Unit,
    private val onFileLongClick: (java.io.File) -> Unit
) : ListAdapter<FileTreeItem, FileTreeAdapter.FileViewHolder>(DIFF) {

    inner class FileViewHolder(private val binding: ItemFileTreeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FileTreeItem) {
            binding.tvFileName.text = item.file.name
            binding.ivIcon.setImageResource(
                if (item.isDirectory) android.R.drawable.ic_menu_more
                else android.R.drawable.ic_menu_edit
            )
            binding.root.setPadding(item.depth * 32, 0, 0, 0)
            binding.root.setOnClickListener { onFileClick(item.file) }
            binding.root.setOnLongClickListener { onFileLongClick(item.file); true }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FileViewHolder(ItemFileTreeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<FileTreeItem>() {
            override fun areItemsTheSame(a: FileTreeItem, b: FileTreeItem) = a.file.path == b.file.path
            override fun areContentsTheSame(a: FileTreeItem, b: FileTreeItem) = a == b
        }
    }
}
