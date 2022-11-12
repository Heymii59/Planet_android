package com.sesac.planet.presentation.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sesac.planet.domain.usecase.GetCurrentUserInfoUseCase

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(GetCurrentUserInfoUseCase()) as T
        }
        throw IllegalArgumentException("MainViewModelFactory error")
    }
}