package com.example.debtcontrol.web

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.debtcontrol.Constants
import com.example.debtcontrol.R
import com.example.debtcontrol.databinding.FragmentWebBinding
import com.example.debtcontrol.web.viewmodel.WebViewModel
import com.example.debtcontrol.web.viewmodel.WebViewModelFactory


class WebFragment : Fragment() {

    private lateinit var binding: FragmentWebBinding

    private val webViewModel: WebViewModel by lazy {
        val arguments = WebFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = WebViewModelFactory(arguments.descResourceId)
        ViewModelProvider(this, viewModelFactory).get(WebViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWebBinding.inflate(inflater)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


        binding.webView.settings.javaScriptEnabled = true

        webViewModel.selectedSettings.observe(viewLifecycleOwner, {
            binding.toolbar.title = getString(it)
            when(it){
                R.string.policy -> binding.webView.loadUrl(Constants.POLICY)
                R.string.about -> binding.webView.loadUrl(Constants.ABOUT)
            }
        })

        return binding.root
    }

    override fun onResume() {
        binding.toolbar.title = getString(webViewModel.selectedSettings.value!!)
        super.onResume()
    }
}