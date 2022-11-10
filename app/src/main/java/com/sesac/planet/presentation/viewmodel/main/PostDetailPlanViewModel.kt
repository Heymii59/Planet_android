package com.sesac.planet.presentation.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.data.model.PostDetailPlanRequest
import com.sesac.planet.data.repository.PlanRepository
import com.sesac.planet.domain.usecase.PostDetailPlanUseCase
import com.sesac.planet.network.PostDetailPlanAPI
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.create

class PostDetailPlanViewModel(private val postDetailPlanUseCase: PostDetailPlanUseCase): ViewModel() {
    private val _detailPlan = MutableLiveData<Response<BaseResponse>>()
    val detailPlan get(): LiveData<Response<BaseResponse>> = _detailPlan

    init {
        PlanRepository.postPlanService = PlanetApplication.getInstance().create(PostDetailPlanAPI::class.java)
    }

    fun postDetailPlan(token: String, journeyId: Int, planetId: Int, postDetailPlanRequest: PostDetailPlanRequest){
        viewModelScope.launch {
            val response = postDetailPlanUseCase(token, journeyId, planetId, postDetailPlanRequest)
            _detailPlan.value = response
        }
    }
}