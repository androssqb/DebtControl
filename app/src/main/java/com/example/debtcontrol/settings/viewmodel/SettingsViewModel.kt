package com.example.debtcontrol.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _settingsSelected = MutableLiveData<Int>()
    val settingsSelected: LiveData<Int>
        get() = _settingsSelected

    fun onSettingsCardClicked(descResourceId: Int) {
        _settingsSelected.value = descResourceId
    }

    fun settingsDone() {
        _settingsSelected.value = null
    }
}