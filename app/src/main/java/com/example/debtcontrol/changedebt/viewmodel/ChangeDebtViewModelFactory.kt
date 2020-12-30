package com.example.debtcontrol.changedebt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.debtcontrol.database.DebtDatabaseDao
import com.example.debtcontrol.database.DebtHistoryDatabaseDao
import java.lang.IllegalArgumentException

class ChangeDebtViewModelFactory(
        private val debtKey: Long,
        private val action: Boolean,
        private val dataSource: DebtDatabaseDao,
        private val dataSourceHistory: DebtHistoryDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangeDebtViewModel::class.java)) {
            return ChangeDebtViewModel(debtKey, action, dataSource, dataSourceHistory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}