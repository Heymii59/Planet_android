package com.sesac.planet.domain.usecase

import com.sesac.planet.data.model.EmailSignUpRequest
import com.sesac.planet.data.repository.AuthRepository

class RequestEmailSignUpUseCase {
    suspend operator fun invoke(request: EmailSignUpRequest) = AuthRepository.requestEmailSignUp( request)
}