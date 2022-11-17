package com.sesac.planet.presentation.viewmodel.main.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.plan.PostDetailPlanRequest
import com.sesac.planet.data.model.plan.PostDetailPlanResponse
import com.sesac.planet.data.repository.main.plan.PlanRepository
import com.sesac.planet.domain.usecase.main.plan.PostDetailPlanUseCase
import com.sesac.planet.network.main.plan.PostDetailPlanAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class PostDetailPlanViewModel(private val postDetailPlanUseCase: PostDetailPlanUseCase) : ViewModel() {
    private val _detailPlan = MutableLiveData<Response<PostDetailPlanResponse>>()
    val detailPlan get(): LiveData<Response<PostDetailPlanResponse>> = _detailPlan

    init {
        PlanRepository.postPlanService = PlanetApplication.getInstance().create(PostDetailPlanAPI::class.java)
    }

    fun postDetailPlan(
        token: String,
        journeyId: Int,
        planetId: Int,
        postDetailPlanRequest: PostDetailPlanRequest
    ) {
        if(detailPlan.value == null){
            viewModelScope.launch {
                val response = postDetailPlanUseCase(token, journeyId, planetId, postDetailPlanRequest)
                _detailPlan.value = response
            }
        }
    }
}