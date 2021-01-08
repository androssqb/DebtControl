package com.example.debtcontrol.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.debtcontrol.R
import com.example.debtcontrol.databinding.FragmentSettingsBinding
import com.example.debtcontrol.settings.adapter.SettingsAdapter
import com.example.debtcontrol.settings.adapter.SettingsListener
import com.example.debtcontrol.settings.data.Datasource
import com.example.debtcontrol.settings.viewmodel.SettingsViewModel

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    private val settingsViewModel: SettingsViewModel by lazy {
        ViewModelProvider(this).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_settings,
                container,
                false
        )
        setSettingsItem()
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        onBack()

        settingsViewModel.navigateToWeb.observe(viewLifecycleOwner, { settings ->
            settings?.let {
                when (it) {
                    R.string.policy -> {
                        findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToWebFragment(it))
                        settingsViewModel.onWebNavigated()
                    }
                    R.string.about -> {
                        findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToWebFragment(it))
                        settingsViewModel.onWebNavigated()
                    }
//                    R.string.rate -> {
//                        findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToWebFragment(it))
//                        settingsViewModel.onWebNavigated()
//                    }
                    R.string.share -> {
                        val intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message))
                            type = "text/plain"
                        }
                        if (intent.resolveActivity(requireActivity().packageManager) != null) {
                            startActivity(intent)
                        }
                    }
                }
            }
        })
        return binding.root
    }

    //Сonnecting and displaying a list of settings
    private fun setSettingsItem() {
        val myDataset = Datasource().loadSettings()
        binding.recyclerView.adapter = SettingsAdapter(requireContext(), myDataset,
                SettingsListener { descResourceId ->
                    settingsViewModel.onSettingsCardClicked(descResourceId)
                })
    }

    //Back button method
    private fun onBack() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}