package com.sesac.planet.presentation.viewmodel.main.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.data.repository.main.plan.PlanRepository
import com.sesac.planet.domain.usecase.main.plan.PatchDetailPlanUseCase
import com.sesac.planet.network.main.plan.PostDetailPlanAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class PatchDetailPlanViewModel(private val patchDetailPlanUseCase: PatchDetailPlanUseCase) :
    ViewModel() {
    private val _patchDetailPlan = MutableLiveData<Response<BaseResponse>>()
    val patchDetailPlan get(): LiveData<Response<BaseResponse>> = _patchDetailPlan

    init {
        PlanRepository.postPlanService =
            PlanetApplication.getInstance().create(PostDetailPlanAPI::class.java)
    }

    fun patchDetailPlan(token: String, detailedPlanId: Int) {
        viewModelScope.launch {
            val response = patchDetailPlanUseCase(token, detailedPlanId)
            _patchDetailPlan.value = response
        }
    }
}