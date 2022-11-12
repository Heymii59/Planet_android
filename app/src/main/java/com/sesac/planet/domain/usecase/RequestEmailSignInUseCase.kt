package com.sesac.planet.domain.usecase

import com.sesac.planet.data.model.EmailSignInRequest
import com.sesac.planet.data.repository.AuthRepository

class RequestEmailSignInUseCase {
    suspend operator fun invoke(request: EmailSignInRequest) = AuthRepository.requestEmailSignIn(request)
}