package com.sesac.planet.domain.usecase

import com.sesac.planet.data.repository.main.plan.PlanRepository

class CheckNickNameUseCase {
    suspend operator fun invoke(nickName: String) = PlanRepository.checkDuplicateNickName(nickName)
}