package com.sesac.planet.presentation.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sesac.planet.domain.usecase.CheckNickNameUseCase
import com.sesac.planet.domain.usecase.MakeJourneyUseCase
import com.sesac.planet.presentation.viewmodel.SettingsViewModel

class SettingsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(MakeJourneyUseCase(), CheckNickNameUseCase()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel from SettingsViewModelFactory")
    }
}