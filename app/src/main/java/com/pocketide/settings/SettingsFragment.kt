package com.pocketide.settings

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.pocketide.databinding.FragmentSettingsBinding
import com.pocketide.ui.ThemeManager

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val themes = listOf("vs-dark", "vs-light", "hc-black", "hc-light")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, themes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTheme.adapter = adapter

        val currentTheme = ThemeManager.getTheme(requireContext())
        binding.spinnerTheme.setSelection(themes.indexOf(currentTheme).coerceAtLeast(0))

        binding.spinnerTheme.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                ThemeManager.saveTheme(requireContext(), themes[pos])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.switchDarkMode.isChecked = ThemeManager.isDarkMode(requireContext())
        binding.switchDarkMode.setOnCheckedChangeListener { _, checked ->
            ThemeManager.saveDarkMode(requireContext(), checked)
            ThemeManager.applyDarkMode(checked)
        }

        binding.switchLineNumbers.isChecked = true
        binding.switchWordWrap.isChecked = false

        binding.btnSaveSettings.setOnClickListener {
            // Save all settings via SharedPreferences
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
