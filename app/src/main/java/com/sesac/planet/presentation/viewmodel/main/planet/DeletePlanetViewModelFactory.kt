package com.sesac.planet.presentation.viewmodel.main.planet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sesac.planet.domain.usecase.main.planet.DeletePlanetUseCase

class DeletePlanetViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DeletePlanetViewModel::class.java)){
            return DeletePlanetViewModel(DeletePlanetUseCase()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel from ExampleViewModelFactory")
    }
}