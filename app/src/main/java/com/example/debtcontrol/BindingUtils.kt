package com.example.debtcontrol

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.debtcontrol.database.Debt
import com.example.debtcontrol.database.DebtHistory
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("name")
fun TextView.setName(item: Debt?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("sum")
fun TextView.setSum(item: Debt?) {
    item?.let {
        text = item.sum.toString()
    }
}

@BindingAdapter("repaymentDate")
fun TextView.setRepaymentDate(item: Debt?) {
    item?.let {
        text = SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(item.debtRepaymentDate)
    }
}

@BindingAdapter("currency")
fun ImageView.setCurrency(item: Debt?) {
    item?.let {
        setImageResource(
                when (item.currency) {
                    "RUB" -> R.drawable.ic__rub
                    "USD" -> R.drawable.ic__usd
                    "EUR" -> R.drawable.ic__eur
                    else -> R.drawable.ic__rub
                }
        )
    }
}

@BindingAdapter("title")
fun MaterialToolbar.setTitle(item: Debt?) {
    item?.let {
        title = item.name
    }
}

@BindingAdapter("debtDetailsCollectionDate")
fun TextView.setDebtDetailsCollectionDate(item: Debt?) {
    item?.let {
        text = SimpleDateFormat("d MMM yyyy г.", Locale.getDefault()).format(item.debtCollectionDate)
    }
}

@BindingAdapter("debtDetailsRepaymentDate")
fun TextView.setDebtDetailsRepaymentDate(item: Debt?) {
    item?.let {
        text = SimpleDateFormat("d MMM yyyy г.", Locale.getDefault()).format(item.debtRepaymentDate)
    }
}

@BindingAdapter("owner")
fun TextView.setOwner(item: Debt?) {
    item?.let {
        text = when (item.isChecked) {
            false -> context.getString(R.string.first_tab_text)
            else -> context.getString(R.string.second_tab_text)
        }
    }
}

@BindingAdapter("background")
fun ImageView.setBackground(item: DebtHistory?) {
    item?.let {
        setImageResource(
                when (item.took) {
                    true -> R.drawable.ic_debt_history_gradient_first
                    else -> R.drawable.ic_debt_history_gradient_second
                }
        )
    }
}

@BindingAdapter("icon")
fun ImageView.setIcon(item: DebtHistory?) {
    item?.let {
        setImageResource(
                when (item.took) {
                    true -> R.drawable.ic_plus_white
                    else -> R.drawable.ic_minus_white
                }
        )
    }
}

@BindingAdapter("action")
fun TextView.setAction(item: DebtHistory?) {
    item?.let {
        text =
                when (item.took) {
                    true -> context.getString(R.string.took)
                    else -> context.getString(R.string.returned)
                }
    }
}

@BindingAdapter("comment")
fun TextView.setComment(item: DebtHistory?) {
    item?.let {
        text = item.comment
    }
}

@BindingAdapter("sumHistory")
fun TextView.setSumHistory(item: DebtHistory?) {
    item?.let {
        text = item.sum.toString()
    }
}

@BindingAdapter("currencyHistory")
fun ImageView.setCurrencyHistory(item: DebtHistory?) {
    item?.let {
        setImageResource(
                when (item.currency) {
                    "RUB" -> R.drawable.ic__rub
                    "USD" -> R.drawable.ic__usd
                    "EUR" -> R.drawable.ic__eur
                    else -> R.drawable.ic__rub
                }
        )
    }
}

@BindingAdapter("date")
fun TextView.setDate(item: DebtHistory?) {
    item?.let {
        text = SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(item.date)
    }
}