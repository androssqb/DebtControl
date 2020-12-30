package com.example.debtcontrol.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DebtDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(debt: Debt)

    @Update
    suspend fun update(debt: Debt)

    @Query("DELETE from debt_list_table WHERE debtId = :key")
    suspend fun deleteDebt(key: Long)

    @Query("SELECT * from debt_list_table WHERE debtId = :key")
    fun getDebtWithId(key: Long): LiveData<Debt>

    @Query("SELECT * FROM debt_list_table WHERE is_checked = :key")
    fun getDebts(key: Boolean): LiveData<List<Debt>>

    @Query("SELECT * FROM debt_list_table ORDER By debtId DESC LIMIT 1")
    suspend fun getLastDebt(): Debt?

}