package com.sesac.planet.presentation.viewmodel.main.planet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sesac.planet.domain.usecase.main.planet.CreateNewPlanetUseCase

class NewPlanetViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewPlanetViewModel::class.java)){
            return NewPlanetViewModel(CreateNewPlanetUseCase()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel from ExampleViewModelFactory")
    }
}