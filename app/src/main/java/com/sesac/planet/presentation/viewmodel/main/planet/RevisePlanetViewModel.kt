package com.sesac.planet.presentation.viewmodel.main.planet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.data.model.planet.RevisePlanetRequest
import com.sesac.planet.data.repository.main.planet.RevisePlanetRepository
import com.sesac.planet.domain.usecase.main.planet.RevisePlanetUseCase
import com.sesac.planet.network.main.planet.RevisePlanetAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class RevisePlanetViewModel(private val revisePlanetUseCase: RevisePlanetUseCase): ViewModel() {
    private val _revisePlanetData = MutableLiveData<Response<BaseResponse>>()
    val revisePlanetData: LiveData<Response<BaseResponse>> get() = _revisePlanetData

    init {
        RevisePlanetRepository.revisePlanetService = PlanetApplication.getInstance().create(RevisePlanetAPI::class.java)
    }

    fun revisePlanet(token: String, planetId: Int, revisePlanetRequest: RevisePlanetRequest){
        viewModelScope.launch {
            val response = revisePlanetUseCase(token, planetId, revisePlanetRequest)
            _revisePlanetData.value = response
        }
    }
}