package com.sesac.planet.presentation.viewmodel.main.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.data.repository.main.plan.PlanRepository
import com.sesac.planet.domain.usecase.main.plan.DeleteDetailPlanUseCase
import com.sesac.planet.network.main.plan.DeleteDetailPlanAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class DeleteDetailPlanViewModel(private val deleteDetailPlanUseCase: DeleteDetailPlanUseCase):
ViewModel(){
    private val _deleteDetailPlan = MutableLiveData<Response<BaseResponse>>()
    val deleteDetailPlan get(): LiveData<Response<BaseResponse>> = _deleteDetailPlan

    init {
        PlanRepository.deletePlanService = PlanetApplication.getInstance().create(DeleteDetailPlanAPI::class.java)
    }

    fun deleteDetailPlan(token: String, detailedPlanId: Int){
        viewModelScope.launch {
            val response = deleteDetailPlanUseCase(token, detailedPlanId)
            _deleteDetailPlan.value = response
        }
    }
}