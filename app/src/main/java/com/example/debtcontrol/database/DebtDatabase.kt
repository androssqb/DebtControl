package com.example.debtcontrol.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Debt::class], version = 2, exportSchema = false)
abstract class DebtDatabase : RoomDatabase() {

    abstract val debtDatabaseDao: DebtDatabaseDao


    companion object {

        @Volatile
        private var INSTANCE: DebtDatabase? = null

        fun getInstance(context: Context): DebtDatabase {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DebtDatabase::class.java,
                        "debt_list_database"
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