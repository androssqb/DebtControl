package com.example.debtcontrol.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.debtcontrol.settings.model.AndroidConfig
import com.example.debtcontrol.settings.viewmodel.SettingsViewModel
import com.google.android.play.core.review.ReviewManagerFactory

class SettingsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding: FragmentSettingsBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_settings,
                container,
                false
        )

        val settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val adapter = SettingsAdapter(SettingsListener { descResourceId ->
            settingsViewModel.onSettingsCardClicked(descResourceId)
        })
        adapter.data = Datasource().loadSettings()
        binding.recyclerView.adapter = adapter

        settingsViewModel.settingsSelected.observe(viewLifecycleOwner, { settings ->
            settings?.let {
                when (it) {
                    R.string.policy -> {
                        findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToWebFragment(it))
                        settingsViewModel.settingsDone()
                    }
                    R.string.about -> {
                        findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToWebFragment(it))
                        settingsViewModel.settingsDone()
                    }
                    R.string.rate -> {
                        val applicationContext = context?.applicationContext
                        val activity = activity
                        if(applicationContext != null && activity != null) {
                            val androidConfig = AndroidConfig(applicationContext, activity)
                            requestReview(androidConfig)
                        }
                        settingsViewModel.settingsDone()
                    }
                    R.string.share -> {
                        if (getShareIntent().resolveActivity(requireActivity().packageManager) != null) {
                            startActivity(getShareIntent())
                            settingsViewModel.settingsDone()
                        }
                    }
                }
            }
        })

        return binding.root
    }

    private fun getShareIntent(): Intent {
        return Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message))
            type = "text/plain"
        }
    }

    private fun requestReview(androidConfig: AndroidConfig) {
        val manager = ReviewManagerFactory.create(androidConfig.applicationContext)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                val reviewInfo = request.result
                val flow = manager.launchReviewFlow(androidConfig.activity, reviewInfo)
                flow.addOnCompleteListener {
                    Toast.makeText(androidConfig.applicationContext, "In App Rating complete", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(androidConfig.applicationContext, "In App Rating failure: requestReviewFlow", Toast.LENGTH_LONG).show()
            }
        }
    }
}