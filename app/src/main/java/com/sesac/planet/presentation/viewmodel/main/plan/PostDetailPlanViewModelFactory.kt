package com.sesac.planet.presentation.viewmodel.main.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sesac.planet.domain.usecase.main.plan.PostDetailPlanUseCase

class PostDetailPlanViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostDetailPlanViewModel::class.java)){
            return PostDetailPlanViewModel(PostDetailPlanUseCase()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel from ExampleViewModelFactory")
    }
}