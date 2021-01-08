package com.example.debtcontrol.addcard.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.debtcontrol.R
import com.example.debtcontrol.database.Debt
import com.example.debtcontrol.database.DebtDatabaseDao
import com.example.debtcontrol.database.DebtHistory
import com.example.debtcontrol.database.DebtHistoryDatabaseDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddCardViewModel(
        page: Int,
        dataSource: DebtDatabaseDao,
        dataSourceHistory: DebtHistoryDatabaseDao,
        application: Application
) : ViewModel() {


    //Datasource variables
    val database = dataSource //DebtDatabase
    private val databaseHistory = dataSourceHistory //DebtHistoryDatabase

    private var name: String = "" //Debt entity variable
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

    private val _isChecked = MutableLiveData<Boolean>()
    val isChecked: LiveData<Boolean>
        get() = _isChecked

    init {
        _isChecked.value = setSwitchPosition(page)
        _isDatePikerDialogShowing.value = false
        _repaymentDate.value =
                SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(System.currentTimeMillis())
        _currencyList.value = application.resources.getStringArray(R.array.currency)
    }

    fun onSwitchCheckedChange(checked: Boolean) {
        _isChecked.value = checked
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
        val sum = textSum.toLong()
        val isChecked = _isChecked.value.toString().toBoolean()
        viewModelScope.launch {
            database.insert(Debt(0, isChecked, name, sum, currency, collDate, repDate))
            val lastDebt = database.getLastDebt() ?: return@launch
            databaseHistory.insert(DebtHistory(0, lastDebt.debtId, true, sum, currency, comment, repDate))
        }
    }

    fun setSwitchPosition(page: Int): Boolean {
        return when (page) {
            0 -> false
            else -> true
        }
    }
}