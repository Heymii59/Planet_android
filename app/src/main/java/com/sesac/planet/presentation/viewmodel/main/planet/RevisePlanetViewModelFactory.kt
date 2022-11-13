package com.sesac.planet.presentation.viewmodel.main.planet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sesac.planet.domain.usecase.main.planet.RevisePlanetUseCase

class RevisePlanetViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RevisePlanetViewModel::class.java)){
            return RevisePlanetViewModel(RevisePlanetUseCase()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel from ExampleViewModelFactory")
    }
}