package com.example.debtcontrol.debtlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class DebtListViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DebtListViewModel::class.java)){
            return DebtListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}