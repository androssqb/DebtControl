package com.example.debtcontrol.debtdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.debtcontrol.database.Debt
import com.example.debtcontrol.database.DebtDatabaseDao
import com.example.debtcontrol.database.DebtHistoryDatabaseDao
import kotlinx.coroutines.launch

class DebtDetailViewModel(
        private val debtKey: Long,
        dataSource: DebtDatabaseDao,
        dataSourceHistory: DebtHistoryDatabaseDao) : ViewModel() {

    val database = dataSource
    private val databaseHistory = dataSourceHistory

    val history = databaseHistory.getDebtHistory(debtKey)

    private val debt: LiveData<Debt>

    fun getDebt() = debt

    private val _navigateToChangeDebtFragment = MutableLiveData<Boolean>()
    val navigateToChangeDebtFragment: LiveData<Boolean>
        get() = _navigateToChangeDebtFragment

    private val _dialogIsShowing = MutableLiveData<Boolean>()
    val dialogIsShowing: LiveData<Boolean>
        get() = _dialogIsShowing

    var action: Boolean = false

    init {
        debt = database.getDebtWithId(debtKey)
        _dialogIsShowing.value = false
        _navigateToChangeDebtFragment.value = false
    }

    fun startDialog() {
        _dialogIsShowing.value = true
    }

    fun dialogShowed() {
        _dialogIsShowing.value = false
    }

    private suspend fun delete() {
        database.deleteDebt(debtKey)
        databaseHistory.deleteDebtHistory(debtKey)
    }

    fun deleteDebt() {
        viewModelScope.launch {
            delete()
        }
    }

    fun startNavigation() {
        _navigateToChangeDebtFragment.value = true
    }

    fun navigationDone() {
        _navigateToChangeDebtFragment.value = false
    }

    fun getAction(action: Boolean) {
        this.action = action
    }
}