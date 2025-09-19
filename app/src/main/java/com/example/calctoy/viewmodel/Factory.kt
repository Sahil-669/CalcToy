package com.example.calctoy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calctoy.data.CalculationRepository

@Suppress("UNCHECKED_CAST")
class CalculationViewModelFactory(
    private val repo: CalculationRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculationViewModel::class.java)) {
            return CalculationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}