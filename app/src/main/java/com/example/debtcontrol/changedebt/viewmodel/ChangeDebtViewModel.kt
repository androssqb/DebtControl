package com.example.debtcontrol.changedebt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.debtcontrol.database.Debt
import com.example.debtcontrol.database.DebtDatabaseDao
import com.example.debtcontrol.database.DebtHistory
import com.example.debtcontrol.database.DebtHistoryDatabaseDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ChangeDebtViewModel(
        private val debtKey: Long,
        private val action: Boolean,
        dataSource: DebtDatabaseDao,
        dataSourceHistory: DebtHistoryDatabaseDao
) : ViewModel() {

    val database = dataSource
    private val databaseHistory = dataSourceHistory


    private var sum: Long = 0L
    private var textSum: String = ""
    private var comment: String = ""
    private var repaymentDate: Long = System.currentTimeMillis()

    private var oldSum: String = ""

    private val debt: LiveData<Debt>
    fun getDebt() = debt

    private val _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date

    private val _isDatePikerDialogShowing = MutableLiveData<Boolean>()
    val isDatePickerDialogShowing: LiveData<Boolean>
        get() = _isDatePikerDialogShowing

    private val _isDebtChanging = MutableLiveData<Boolean>()
    val isDebtChanging: LiveData<Boolean>
        get() = _isDebtChanging

    private val _took = MutableLiveData<Boolean>()
    val took: LiveData<Boolean>
        get() = _took

    private val _isSumNotCorrect = MutableLiveData<Boolean>()
    val isSumNotCorrect: LiveData<Boolean>
        get() = _isSumNotCorrect

    init {
        _took.value = action
        debt = database.getDebtWithId(debtKey)
        _isDebtChanging.value = false
        _isDatePikerDialogShowing.value = false
        _date.value =
                SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(System.currentTimeMillis())
        _isSumNotCorrect.value = false
    }

    fun startDatePikerDialog() {
        _isDatePikerDialogShowing.value = true
    }

    fun datePikerDialogShowed() {
        _isDatePikerDialogShowing.value = false
    }

    fun getDate(time: Long) {
        _date.value =
                SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(time)
        repaymentDate = time
    }

    fun navigateToDebtDetails() {
        _isDebtChanging.value = true
    }

    fun navigationDone() {
        _isDebtChanging.value = false
    }

    fun onSumChanged(text: CharSequence?) {
        textSum = text?.toString() ?: ""
    }

    fun onCommentChanged(text: CharSequence?) {
        comment = text?.toString() ?: ""
    }

    fun getOldSum(text: CharSequence?) {
        oldSum = text?.toString() ?: ""
    }

    fun sumCheck(): Boolean {
        sum = textSum.toLong()
        val checkSum = oldSum.toLong()
        if (!action) {
            if (checkSum - sum < 0) {
                _isSumNotCorrect.value = true
                return false
            }
        }
        return true
    }

    fun sumCorrect(){
        _isSumNotCorrect.value = false
    }

    private fun changeSum(sum: Long, historySum: Long): Long {
        return if (action) {
            sum + historySum
        } else {
            sum - historySum
        }
    }

    fun onSaveClick() {
        sum = textSum.toLong()
        viewModelScope.launch {
            val oldDebt = debt.value ?: return@launch
            val currency = oldDebt.currency
            databaseHistory.insert(DebtHistory(0, debtKey, action, sum, currency, comment, repaymentDate))
        }

        viewModelScope.launch {
            val newDebt = debt.value ?: return@launch
            newDebt.sum = changeSum(newDebt.sum, sum)
            database.update(newDebt)
        }
    }
}