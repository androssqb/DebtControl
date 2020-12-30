package com.example.debtcontrol.addcard.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.debtcontrol.R
import com.example.debtcontrol.database.Debt
import com.example.debtcontrol.database.DebtDatabaseDao
import com.example.debtcontrol.database.DebtHistory
import com.example.debtcontrol.database.DebtHistoryDatabaseDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddCardViewModel(
        dataSource: DebtDatabaseDao,
        dataSourceHistory: DebtHistoryDatabaseDao,
        application: Application
) : ViewModel() {


    //Datasource variables
    val database = dataSource //DebtDatabase
    private val databaseHistory = dataSourceHistory //DebtHistoryDatabase

    private var isChecked: Boolean = false //Debt entity variable
    private var name: String = "" //Debt entity variable
    private var sum: Long = 0L //Debt & //Debt history entity variable
    private var currency: String = "RUB" //Debt entity variable
    private val collDate: Long = System.currentTimeMillis() //Debt entity variable
    private var repDate: Long = collDate //Debt entity variable

    private var textSum: String = "" //Variable for sum conversion

    private var comment: String = "" //Debt history  entity variable

    private val _currencyList = MutableLiveData<Array<String>>()
    val currencyList: LiveData<Array<String>>
        get() = _currencyList

    private val _repaymentDate = MutableLiveData<String>()
    val repaymentDate: LiveData<String>
        get() = _repaymentDate

    private val _isDatePikerDialogShowing = MutableLiveData<Boolean>()
    val isDatePickerDialogShowing: LiveData<Boolean>
        get() = _isDatePikerDialogShowing

    init {
        _isDatePikerDialogShowing.value = false
        _repaymentDate.value =
                SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(System.currentTimeMillis())
        _currencyList.value = application.resources.getStringArray(R.array.currency)
    }

    fun onSwitchCheckedChange(checked: Boolean) {
        isChecked = checked
    }

    fun onNameChanged(text: CharSequence?) {
        name = text?.toString() ?: ""
    }

    fun onSumChanged(text: CharSequence?) {
        textSum = text?.toString() ?: ""
    }

    fun onCurrencyChanged(text: CharSequence?) {
        currency = text?.toString() ?: "RUB"
    }

    fun onCommentChanged(text: CharSequence?) {
        comment = text?.toString() ?: ""
    }

    fun startDatePikerDialog() {
        _isDatePikerDialogShowing.value = true
    }

    fun datePikerDialogShowed() {
        _isDatePikerDialogShowing.value = false
    }

    fun getRepaymentDate(time: Long) {
        _repaymentDate.value =
                SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(time)
        repDate = time
    }

    fun onSaveClick() {
        sum = textSum.toLong()
        viewModelScope.launch {
            database.insert(Debt(0, isChecked, name, sum, currency, collDate, repDate))
            val lastDebt = database.getLastDebt() ?: return@launch
            databaseHistory.insert(DebtHistory(0, lastDebt.debtId, true, sum, currency, comment, repDate))
        }
    }
}