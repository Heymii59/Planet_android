package com.sesac.planet.presentation.viewmodel.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sesac.planet.domain.usecase.main.home.GetKeywordUseCase
import com.sesac.planet.presentation.viewmodel.main.KeywordViewModel

class KeywordViewModelFactory : ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(KeywordViewModel::class.java)){
            return KeywordViewModel(GetKeywordUseCase()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel from ExampleViewModelFactory")
    }
}