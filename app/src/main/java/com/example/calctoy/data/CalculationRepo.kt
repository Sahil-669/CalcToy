package com.example.calctoy.data

import com.example.calctoy.data.db.Dao
import com.example.calctoy.data.db.CalculationEntity
import kotlinx.coroutines.flow.Flow

class CalculationRepository(private val dao: Dao) {
    suspend fun insertCalculation(calc: CalculationEntity) = dao.insertCalculation(calc)
    fun getAllCalculations(): Flow<List<CalculationEntity>> = dao.getAllCalculations()
    suspend fun clearHistory() = dao.clearHistory()
}