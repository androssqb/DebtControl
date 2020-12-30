package com.example.debtcontrol.mydebtors.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.debtcontrol.database.DebtDatabaseDao

class MyDebtorsViewModelFactory(
    private val dataSource: DebtDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyDebtorsViewModel::class.java)) {
            return MyDebtorsViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}