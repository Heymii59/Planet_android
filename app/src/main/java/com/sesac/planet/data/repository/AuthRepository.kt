package com.sesac.planet.data.repository

import com.sesac.planet.data.model.*
import com.sesac.planet.network.LoginAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object AuthRepository {
    lateinit var loginAPI: LoginAPI

    suspend fun requestKakaoLogin(accessToken: String) = withContext(Dispatchers.IO) {
        loginAPI.requestKakaoLogin(accessToken)
    }

    suspend fun requestEmailAuthCode(token: String, email: String): Response<RequestEmailAuthCodeResponse> {
        val response: Response<RequestEmailAuthCodeResponse>
        withContext(Dispatchers.IO) {
            response = loginAPI.requestEmailAuthCode(token, email)
        }
        return response
    }

    suspend fun requestAuthCodeVerify(token: String, request: AuthCodeVerifyRequest): Response<AuthCodeVerifyResponse> {
        val response: Response<AuthCodeVerifyResponse>
        withContext(Dispatchers.IO) {
            response = loginAPI.requestAuthCodeVerify(token, request)
        }
        return response
    }

    suspend fun requestEmailSignUp(request: EmailSignUpRequest): Response<EmailSignUpResponse> {
        val response: Response<EmailSignUpResponse>
        withContext(Dispatchers.IO) {
            response = loginAPI.requestEmailSignUp(request)
        }
        return response
    }

    suspend fun requestEmailSignIn(request: EmailSignInRequest): Response<EmailSignInResponse> {
        val response: Response<EmailSignInResponse>
        withContext(Dispatchers.IO) {
            response = loginAPI.requestEmailSignIn(request)
        }
        return response
    }
}