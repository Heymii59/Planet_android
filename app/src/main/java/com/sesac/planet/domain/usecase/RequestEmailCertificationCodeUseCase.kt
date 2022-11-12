package com.sesac.planet.domain.usecase

import com.sesac.planet.data.repository.AuthRepository

class RequestEmailCertificationCodeUseCase {
    suspend operator fun invoke(token: String, email: String) = AuthRepository.requestEmailAuthCode(token ,email)
}