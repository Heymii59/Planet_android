package com.sesac.planet.presentation.viewmodel.dailyrecord

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.CreateDiaryRequest
import com.sesac.planet.data.model.CreateDiaryResponse
import com.sesac.planet.data.model.QueryDailyRecordRequest
import com.sesac.planet.data.model.QueryDailyRecordResponse
import com.sesac.planet.data.repository.main.dailyrecord.DailyRecordRepository
import com.sesac.planet.domain.usecase.main.MakeDailyRecordUseCase
import com.sesac.planet.domain.usecase.main.QueryDailyRecordUseCase
import com.sesac.planet.network.main.dailyrecord.DailyRecordAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class DailyRecordViewModel(
    private val makeDailyRecordUseCase: MakeDailyRecordUseCase,
    private val queryDailyRecordUseCase: QueryDailyRecordUseCase
) : ViewModel() {
    private val _makeDailyRecordResponse = MutableLiveData<Response<CreateDiaryResponse>>()
    val makeDailyRecordResponse: LiveData<Response<CreateDiaryResponse>> get() = _makeDailyRecordResponse


    init {
        DailyRecordRepository.dailyRecordService = PlanetApplication.getInstance().create(DailyRecordAPI::class.java)
    }

    fun makeDailyRecord(request: CreateDiaryRequest, token: String) {
        viewModelScope.launch {
            _makeDailyRecordResponse.value = makeDailyRecordUseCase(request, token)!!
        }
    }
}