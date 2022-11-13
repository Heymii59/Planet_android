package com.sesac.planet.presentation.viewmodel.main.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.report.GetTodayInfoResponse
import com.sesac.planet.data.repository.main.report.GetTodayInfoRepository
import com.sesac.planet.domain.usecase.main.report.GetTodayInfoUseCase
import com.sesac.planet.network.main.report.GetTodayInfoAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class GetTodayInfoViewModel(private val getTodayInfoUseCase: GetTodayInfoUseCase) : ViewModel() {
    private val _getTodayInfoData = MutableLiveData<Response<GetTodayInfoResponse>>()
    val getTodayInfoData: LiveData<Response<GetTodayInfoResponse>> get() = _getTodayInfoData

    init {
        GetTodayInfoRepository.getTodayInfoService =
            PlanetApplication.getInstance().create(GetTodayInfoAPI::class.java)
    }

    fun getTodayInfo(token: String, userId: Int) {
        viewModelScope.launch {
            val response = getTodayInfoUseCase(token, userId)
            _getTodayInfoData.value = response
        }
    }
}