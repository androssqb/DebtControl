package com.example.debtcontrol.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debt_history_table")
data class DebtHistory(

        @PrimaryKey(autoGenerate = true)
        var debtHistoryId: Long = 0L,

        @ColumnInfo(name = "debt_id")
        val debtId: Long,

        @ColumnInfo(name = "took")
        val took: Boolean,

        @ColumnInfo(name = "sum")
        val sum: Long,

        @ColumnInfo(name = "currency")
        val currency: String,

        @ColumnInfo(name = "comment")
        val comment: String,

        @ColumnInfo(name = "date")
        val date: Long
)