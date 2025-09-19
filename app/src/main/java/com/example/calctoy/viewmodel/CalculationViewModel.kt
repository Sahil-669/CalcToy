package com.example.calctoy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calctoy.data.CalculationRepository
import com.example.calctoy.data.db.CalculationEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class CalculationViewModel(private val repo: CalculationRepository) : ViewModel() {

    val history = repo.getAllCalculations()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    private val _selected = MutableStateFlow<CalculationEntity?>(null)
    val selected: StateFlow<CalculationEntity?> = _selected

    fun selectCalculation(entity: CalculationEntity) {
        _selected.value = entity
    }

    fun addCalculation(expression: String, result: String) {
        val entity = CalculationEntity(expression = expression, result = result)
        viewModelScope.launch { repo.insertCalculation(entity) }
    }

    fun clearHistory() {
        viewModelScope.launch { repo.clearHistory() }
    }
}