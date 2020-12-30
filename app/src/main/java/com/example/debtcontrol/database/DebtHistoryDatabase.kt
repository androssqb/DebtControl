package com.example.debtcontrol.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DebtHistory::class], version = 1, exportSchema = false)
abstract class DebtHistoryDatabase : RoomDatabase() {

    abstract val debtHistoryDatabaseDao: DebtHistoryDatabaseDao


    companion object {

        @Volatile
        private var INSTANCE: DebtHistoryDatabase? = null

        fun getInstance(context: Context): DebtHistoryDatabase {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            DebtHistoryDatabase::class.java,
                            "debt_history_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}