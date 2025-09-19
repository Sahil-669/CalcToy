package com.example.calctoy.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CalculationEntity::class], version = 1, exportSchema = false)
abstract class CalculationDatabase : RoomDatabase() {
    abstract fun Dao(): Dao

    companion object {
        @Volatile private var INSTANCE: CalculationDatabase? = null

        fun getInstance(context: Context): CalculationDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CalculationDatabase::class.java,
                    "calc_history.db"
                ).build().also { INSTANCE = it }
            }
    }
}