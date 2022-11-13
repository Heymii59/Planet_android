package com.sesac.planet.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.CheckNickNameResponse
import com.sesac.planet.data.model.Goal
import com.sesac.planet.data.model.MakeJourneyRequest
import com.sesac.planet.data.model.MakeJourneyResponse
import com.sesac.planet.data.repository.main.plan.PlanRepository
import com.sesac.planet.domain.usecase.CheckNickNameUseCase
import com.sesac.planet.domain.usecase.MakeJourneyUseCase
import com.sesac.planet.network.main.plan.PlanAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class SettingsViewModel(
    private val makeJourneyUseCase: MakeJourneyUseCase,
    private val checkNickNameUseCase: CheckNickNameUseCase
) : ViewModel() {
    var nickName: String = ""
    var period: Int = 26
    var myFutureLookItems: MutableList<String> = mutableListOf()
    var wantToAchieveItems: MutableList<Goal> = mutableListOf()
    var details: MutableList<String> = mutableListOf()


    init {
        PlanRepository.planService = PlanetApplication.getInstance().create(PlanAPI::class.java)
    }

    private val _isSuccessMakeJourney = MutableLiveData<Response<MakeJourneyResponse>>()
    val isSuccessMakeJourney: LiveData<Response<MakeJourneyResponse>> get() = _isSuccessMakeJourney

    private val _isAvailableNickName = MutableLiveData<Response<CheckNickNameResponse>>()
    val isAvailableNickName: LiveData<Response<CheckNickNameResponse>> get() = _isAvailableNickName

    fun makeJourney(journeyRequest: MakeJourneyRequest, token: String, userId: Int) {
        viewModelScope.launch {
            _isSuccessMakeJourney.value = makeJourneyUseCase(journeyRequest, token, userId)!!
        }
    }

    fun checkNickName(nickName: String) {
        viewModelScope.launch {
            _isAvailableNickName.value = checkNickNameUseCase(nickName)
        }
    }

}