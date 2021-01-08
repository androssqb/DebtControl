package com.example.debtcontrol.addcard.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.debtcontrol.database.DebtDatabaseDao
import com.example.debtcontrol.database.DebtHistoryDatabaseDao

class AddCardViewModelFactory(
        private val page: Int,
        private val dataSource: DebtDatabaseDao,
        private val dataSourceHistory: DebtHistoryDatabaseDao,
        private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCardViewModel::class.java)) {
            return AddCardViewModel(page, dataSource, dataSourceHistory, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}