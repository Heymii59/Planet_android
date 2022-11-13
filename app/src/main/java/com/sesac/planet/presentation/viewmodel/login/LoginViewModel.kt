package com.sesac.planet.presentation.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sesac.planet.config.PlanetApplication
import com.sesac.planet.data.model.*
import com.sesac.planet.data.repository.AuthRepository
import com.sesac.planet.data.repository.main.plan.PlanRepository
import com.sesac.planet.domain.usecase.*
import com.sesac.planet.network.LoginAPI
import com.sesac.planet.network.main.plan.PlanAPI
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(
    private val requestEmailCertificationCodeUseCase: RequestEmailCertificationCodeUseCase,
    private val requestKakaoLoginUseCase: RequestKakaoLoginUseCase,
    private val requestAuthCodeVerifyUseCase: RequestAuthCodeVerifyUseCase,
    private val requestEmailSignUpUseCase: RequestEmailSignUpUseCase,
    private val requestEmailSignInUseCase: RequestEmailSignInUseCase,
    private val makeJourneyUseCase: MakeJourneyUseCase
) : ViewModel() {
    private var _requestAuthCodeResponse: MutableLiveData<Response<RequestEmailAuthCodeResponse>> = MutableLiveData()
    val requestAuthCodeResponse: LiveData<Response<RequestEmailAuthCodeResponse>> get() = _requestAuthCodeResponse

    private var _kakaoLoginResponse = MutableLiveData<Response<KakaoLoginResponse>>()
    val kakaoLoginResponse: LiveData<Response<KakaoLoginResponse>> get() = _kakaoLoginResponse

    private var _requestAuthCodeVerifyResponse: MutableLiveData<Response<AuthCodeVerifyResponse>> = MutableLiveData()
    val requestAuthCodeVerifyResponseResponse: LiveData<Response<AuthCodeVerifyResponse>> get() = _requestAuthCodeVerifyResponse

    private var _requestEmailSignUpResponse: MutableLiveData<Response<EmailSignUpResponse>> = MutableLiveData()
    val requestEmailSignUpResponse: LiveData<Response<EmailSignUpResponse>> get() = _requestEmailSignUpResponse

    private var _requestEmailSignInResponse: MutableLiveData<Response<EmailSignInResponse>> = MutableLiveData()
    val requestEmailSignInResponse: LiveData<Response<EmailSignInResponse>> get() = _requestEmailSignInResponse

    var userEmail: String = ""
    var userPassword: String = ""
    var phoneNumber: String = ""

    init {
        AuthRepository.loginAPI = PlanetApplication.getInstance().create(LoginAPI::class.java)
        PlanRepository.planService = PlanetApplication.getInstance().create(PlanAPI::class.java)
    }

    fun requestCertificationCode(token: String, email: String) {
        viewModelScope.launch {
            _requestAuthCodeResponse.value = requestEmailCertificationCodeUseCase(token, email)!!
        }
    }

    fun requestKakaoLogin(accessToken: String) {
        viewModelScope.launch {
            _kakaoLoginResponse.value = requestKakaoLoginUseCase(accessToken)!!
        }
    }

    fun requestAuthCodeVerify(token: String, request: AuthCodeVerifyRequest) {
        viewModelScope.launch {
            _requestAuthCodeVerifyResponse.value = requestAuthCodeVerifyUseCase(token ,request)!!
        }
    }

    fun requestEmailSignUp(request: EmailSignUpRequest) {
        viewModelScope.launch {
            _requestEmailSignUpResponse.value = requestEmailSignUpUseCase(request)!!
        }
    }

    fun requestEmailSignIn(request: EmailSignInRequest) {
        viewModelScope.launch {
            _requestEmailSignInResponse.value = requestEmailSignInUseCase(request)!!
        }
    }

    private val _isSuccessMakeJourney = MutableLiveData<Response<MakeJourneyResponse>>()
    val isSuccessMakeJourney: LiveData<Response<MakeJourneyResponse>> get() = _isSuccessMakeJourney

    fun makeJourney(journeyRequest: MakeJourneyRequest, token: String, userId: Int) {
        viewModelScope.launch {
            _isSuccessMakeJourney.value = makeJourneyUseCase(journeyRequest, token, userId)!!
        }
    }
}