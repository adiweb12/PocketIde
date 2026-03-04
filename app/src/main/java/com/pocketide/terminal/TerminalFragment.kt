package com.pocketide.terminal

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pocketide.databinding.FragmentTerminalBinding

class TerminalFragment : Fragment() {

    private var _binding: FragmentTerminalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TerminalViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTerminalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.output.observe(viewLifecycleOwner) { output ->
            binding.tvOutput.text = output
            binding.scrollView.post { binding.scrollView.fullScroll(View.FOCUS_DOWN) }
        }

        binding.etInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                val cmd = binding.etInput.text.toString()
                binding.etInput.text?.clear()
                viewModel.runCommand(cmd)
                true
            } else false
        }

        binding.btnRun.setOnClickListener {
            val cmd = binding.etInput.text.toString()
            binding.etInput.text?.clear()
            viewModel.runCommand(cmd)
        }

        binding.btnClear.setOnClickListener { viewModel.clearOutput() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
