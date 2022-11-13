package com.sesac.planet.presentation.viewmodel.main.planet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.data.repository.main.planet.DeletePlanetRepository
import com.sesac.planet.domain.usecase.main.planet.DeletePlanetUseCase
import com.sesac.planet.network.main.planet.DeletePlanetAPI
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.create

class DeletePlanetViewModel(private val deletePlanetUseCase: DeletePlanetUseCase): ViewModel() {
    private val _deletePlanetData = MutableLiveData<Response<BaseResponse>>()
    val deletePlanetData: LiveData<Response<BaseResponse>> get() = _deletePlanetData

    init {
        DeletePlanetRepository.deletePlanetService = PlanetApplication.getInstance().create(DeletePlanetAPI::class.java)
    }

    fun deletePlanet(token: String, planetId: Int){
        viewModelScope.launch {
            val response = deletePlanetUseCase(token, planetId)
            _deletePlanetData.value = response
        }
    }
}