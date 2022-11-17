package com.sesac.planet.presentation.viewmodel.dailyrecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sesac.planet.domain.usecase.main.QueryDailyRecordUseCase

class PastDailyRecordViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PastDailyRecordViewModel::class.java)) {
            return PastDailyRecordViewModel(QueryDailyRecordUseCase()) as T
        } else {
            throw IllegalArgumentException("PastDailyRecordViewModelFactory error")
        }
    }

}