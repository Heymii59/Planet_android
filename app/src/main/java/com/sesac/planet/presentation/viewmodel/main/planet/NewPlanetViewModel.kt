package com.sesac.planet.presentation.viewmodel.main.planet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.data.model.planet.CreateNewPlanetRequest
import com.sesac.planet.data.repository.main.planet.PostPlanetRepository
import com.sesac.planet.domain.usecase.main.planet.CreateNewPlanetUseCase
import com.sesac.planet.network.main.planet.PostPlanetAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class NewPlanetViewModel(private val createNewPlanetUseCase: CreateNewPlanetUseCase) : ViewModel() {
    private val _newPlanetData = MutableLiveData<Response<BaseResponse>>()
    val newPlanetData: LiveData<Response<BaseResponse>> get() = _newPlanetData

    init {
        PostPlanetRepository.createNewPlanetService =
            PlanetApplication.getInstance().create(PostPlanetAPI::class.java)
    }

    fun createNewPlanet(token: String, journeyId: Int, createNewPlanetRequest:CreateNewPlanetRequest) {
        viewModelScope.launch {
            val response = createNewPlanetUseCase(token, journeyId, createNewPlanetRequest)
            _newPlanetData.value = response
        }
    }
}