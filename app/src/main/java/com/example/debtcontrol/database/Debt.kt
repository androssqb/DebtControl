package com.example.debtcontrol.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "debt_list_table")
data class Debt(
        @PrimaryKey(autoGenerate = true)
        var debtId: Long = 0L,

        @ColumnInfo(name = "is_checked")
        val isChecked: Boolean,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "sum")
        var sum: Long,

        @ColumnInfo(name = "currency")
        var currency: String,

        @ColumnInfo(name = "debt_collection_date")
        var debtCollectionDate: Long,

        @ColumnInfo(name = "debt_repayment_date")
        var debtRepaymentDate: Long
)