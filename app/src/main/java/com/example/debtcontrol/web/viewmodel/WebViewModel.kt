package com.example.debtcontrol.web.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WebViewModelFactory(
    private val descResourceId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WebViewModel::class.java)) {
            return WebViewModel(descResourceId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class WebViewModel(descResourceId: Int) : ViewModel() {

    private val _selectedSettings = MutableLiveData<Int>()
    val selectedSettings: LiveData<Int>
        get() = _selectedSettings

    init {
        _selectedSettings.value = descResourceId
    }
}