package com.example.debtcontrol.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DebtHistoryDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(debtHistory: DebtHistory)

    @Update
    suspend fun update(debtHistory: DebtHistory)

    @Query("DELETE from debt_history_table WHERE debt_id = :key")
    suspend fun deleteDebtHistory(key: Long)

    @Query("SELECT * from debt_history_table WHERE debt_id = :key")
    fun getDebtHistory(key: Long): LiveData<List<DebtHistory>>
}