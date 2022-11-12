package com.sesac.planet.network

import com.sesac.planet.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginAPI {

    @POST("/oauth/create/kakao")
    suspend fun requestKakaoLogin(
        @Body access_token: String
    ): Response<KakaoLoginResponse>

    @POST("/mail")
    suspend fun requestEmailAuthCode(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body email: String
    ): Response<RequestEmailAuthCodeResponse>

    @POST("/mail/auth")
    suspend fun requestAuthCodeVerify(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: AuthCodeVerifyRequest
    ): Response<AuthCodeVerifyResponse>

    @POST("/users/create")
    suspend fun requestEmailSignUp(
        @Body request: EmailSignUpRequest
    ): Response<EmailSignUpResponse>

    @POST("/users/logIn")
    suspend fun requestEmailSignIn(
        @Body request: EmailSignInRequest
    ): Response<EmailSignInResponse>
}