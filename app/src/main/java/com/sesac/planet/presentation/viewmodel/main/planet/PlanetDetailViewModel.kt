package com.sesac.planet.presentation.viewmodel.main.planet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.planet.PlanetDetailInfoResponse
import com.sesac.planet.data.repository.main.planet.PlanetDetailRepository
import com.sesac.planet.domain.usecase.main.planet.GetPlanetDetailInfoUseCase
import com.sesac.planet.network.main.planet.PlanetDetailAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class PlanetDetailViewModel(private val getPlanetDetailInfoUseCase: GetPlanetDetailInfoUseCase) :
    ViewModel() {
    private val _planetDetailData = MutableLiveData<Response<PlanetDetailInfoResponse>>()
    val planetDetailData: LiveData<Response<PlanetDetailInfoResponse>> get() = _planetDetailData

    init {
        PlanetDetailRepository.planetDetailService = PlanetApplication.getInstance().create(
            PlanetDetailAPI::class.java
        )
    }

    fun getPlanetDetailInfo(token: String, planetId: Int) {
        viewModelScope.launch {
            val response = getPlanetDetailInfoUseCase(token, planetId)
            _planetDetailData.value = response
        }

    }
}