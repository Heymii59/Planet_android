package com.sesac.planet.presentation.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.mypage.CurrentUserInfoResponse
import com.sesac.planet.data.repository.main.mypage.MyPageRepository
import com.sesac.planet.domain.usecase.GetCurrentUserInfoUseCase
import com.sesac.planet.network.LoginAPI
import com.sesac.planet.network.main.mypage.MyPageAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    private val getCurrentUserInfoUseCase: GetCurrentUserInfoUseCase
) : ViewModel() {
    private var _currentUserInfoResponse: MutableLiveData<Response<CurrentUserInfoResponse>> = MutableLiveData()
    val currentUserInfoResponse: LiveData<Response<CurrentUserInfoResponse>> get() = _currentUserInfoResponse

    init {
        MyPageRepository.myPageAPI = PlanetApplication.getInstance().create(MyPageAPI::class.java)
    }

    fun getCurrentUserInfo(token: String, userIdx: Int) {
        viewModelScope.launch {
            _currentUserInfoResponse.value = getCurrentUserInfoUseCase(token, userIdx)!!
        }
    }
}