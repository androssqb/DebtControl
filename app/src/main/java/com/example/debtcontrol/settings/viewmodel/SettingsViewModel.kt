package com.example.debtcontrol.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _navigateToWeb = MutableLiveData<Int>()
    val navigateToWeb: LiveData<Int>
        get() = _navigateToWeb

    fun onSettingsCardClicked(descResourceId: Int) {
        _navigateToWeb.value = descResourceId
    }

    fun onWebNavigated() {
        _navigateToWeb.value = null
    }
}