package com.sesac.planet.presentation.viewmodel.dailyrecord

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.QueryDailyRecordRequest
import com.sesac.planet.data.model.QueryDailyRecordResponse
import com.sesac.planet.data.repository.main.dailyrecord.DailyRecordRepository
import com.sesac.planet.domain.usecase.main.QueryDailyRecordUseCase
import com.sesac.planet.network.main.dailyrecord.DailyRecordAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class PastDailyRecordViewModel(
    private val queryDailyRecordUseCase: QueryDailyRecordUseCase
) : ViewModel() {

    init {
        DailyRecordRepository.dailyRecordService = PlanetApplication.getInstance().create(DailyRecordAPI::class.java)
    }

    private val _queryDailyRecordResponse = MutableLiveData<Response<QueryDailyRecordResponse>>()
    val queryDailyRecordResponse: LiveData<Response<QueryDailyRecordResponse>> get() = _queryDailyRecordResponse

    fun queryDailyRecord(startDate: String, endDate: String, token: String, userId: Int) {
        viewModelScope.launch {
            _queryDailyRecordResponse.value = queryDailyRecordUseCase(startDate, endDate, token, userId)!!
        }
    }
}