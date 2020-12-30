package com.example.debtcontrol.debtdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.debtcontrol.database.DebtDatabaseDao
import com.example.debtcontrol.database.DebtHistoryDatabaseDao
import java.lang.IllegalArgumentException

class DebtDetailViewModelFactory(
        private val debtKey: Long,
        private val dataSource: DebtDatabaseDao,
        private val dataSourceHistory: DebtHistoryDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DebtDetailViewModel::class.java)) {
            return DebtDetailViewModel(debtKey, dataSource, dataSourceHistory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}