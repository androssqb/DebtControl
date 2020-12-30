package com.example.debtcontrol.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.debtcontrol.R
import com.example.debtcontrol.databinding.FragmentSettingsBinding
import com.example.debtcontrol.settings.adapter.SettingsAdapter
import com.example.debtcontrol.settings.data.Datasource

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

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
        return binding.root
    }

    //Сonnecting and displaying a list of settings
    private fun setSettingsItem() {
        val myDataset = Datasource().loadSettings()
        binding.recyclerView.adapter = SettingsAdapter(requireContext(), myDataset)
    }

    //Back button method
    private fun onBack() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}