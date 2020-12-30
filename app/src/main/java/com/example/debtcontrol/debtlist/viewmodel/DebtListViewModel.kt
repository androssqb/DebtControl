package com.example.debtcontrol.debtlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DebtListViewModel : ViewModel() {

    private val _navigateToAddCardFragment = MutableLiveData<Boolean>()
    val navigateToAddCardFragment: LiveData<Boolean>
        get() = _navigateToAddCardFragment

    init {
        _navigateToAddCardFragment.value = false
    }

    fun startNavigation() {
        _navigateToAddCardFragment.value = true
    }

    fun navigationDone() {
        _navigateToAddCardFragment.value = false
    }
}