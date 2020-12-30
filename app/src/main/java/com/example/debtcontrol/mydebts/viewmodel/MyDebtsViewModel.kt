package com.example.debtcontrol.mydebts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.debtcontrol.database.DebtDatabaseDao

class MyDebtsViewModel(
        dataSource: DebtDatabaseDao
) : ViewModel() {
    val database = dataSource
    val debts = database.getDebts(true)

    private val _navigateToDebtDetail = MutableLiveData<Long>()
    val navigateToDebtDetail: LiveData<Long>
        get() = _navigateToDebtDetail

    fun onDebtCardClicked(id: Long) {
        _navigateToDebtDetail.value = id
    }

    fun onDebtDetailNavigated() {
        _navigateToDebtDetail.value = null
    }
}