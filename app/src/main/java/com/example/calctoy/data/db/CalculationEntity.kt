package com.example.calctoy.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Calculations")
data class CalculationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val expression: String,
    val result: String,
    val timestamp: Long = System.currentTimeMillis()
)