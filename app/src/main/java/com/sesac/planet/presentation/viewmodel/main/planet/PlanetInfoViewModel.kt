package com.sesac.planet.presentation.viewmodel.main.planet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.planet.PlanetInfoResponse
import com.sesac.planet.data.repository.main.planet.PlanetRepository
import com.sesac.planet.domain.usecase.main.planet.GetPlanetUseCase
import com.sesac.planet.network.main.planet.PlanetInfoAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class PlanetInfoViewModel(private val getPlanetUseCase: GetPlanetUseCase) : ViewModel() {
    private val _planetData = MutableLiveData<Response<PlanetInfoResponse>>()
    val planetData get(): LiveData<Response<PlanetInfoResponse>> = _planetData

    init {
        PlanetRepository.infoService =
            PlanetApplication.getInstance().create(PlanetInfoAPI::class.java)
    }

    fun getPlanet(token: String, journeyId: Int) {
        viewModelScope.launch {
            val response = getPlanetUseCase(token, journeyId)
            _planetData.value = response
        }
    }
}