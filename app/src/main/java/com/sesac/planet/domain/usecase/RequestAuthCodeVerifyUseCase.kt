package com.sesac.planet.domain.usecase

import com.sesac.planet.data.model.AuthCodeVerifyRequest
import com.sesac.planet.data.repository.AuthRepository

class RequestAuthCodeVerifyUseCase {
    suspend operator fun invoke(token: String, request: AuthCodeVerifyRequest) = AuthRepository.requestAuthCodeVerify(token, request)
}