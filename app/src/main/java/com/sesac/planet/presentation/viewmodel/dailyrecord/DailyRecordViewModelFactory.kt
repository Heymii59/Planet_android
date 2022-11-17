package com.sesac.planet.presentation.viewmodel.dailyrecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sesac.planet.domain.usecase.main.MakeDailyRecordUseCase
import com.sesac.planet.domain.usecase.main.QueryDailyRecordUseCase

class DailyRecordViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DailyRecordViewModel::class.java)) {
            return DailyRecordViewModel(MakeDailyRecordUseCase(), QueryDailyRecordUseCase()) as T
        }
        throw IllegalArgumentException("DailyRecordViewModelFactory error")
    }
}