package com.sesac.planet.presentation.viewmodel.main.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sesac.planet.domain.usecase.main.plan.DeleteDetailPlanUseCase

class DeleteDetailPlanViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DeleteDetailPlanViewModel::class.java)){
            return DeleteDetailPlanViewModel(DeleteDetailPlanUseCase()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel from ExampleViewModelFactory")
    }
}