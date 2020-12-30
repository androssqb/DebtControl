package com.example.debtcontrol.mydebts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.debtcontrol.database.DebtDatabaseDao

class MyDebtsViewModelFactory(
        private val dataSource: DebtDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyDebtsViewModel::class.java)) {
            return MyDebtsViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}