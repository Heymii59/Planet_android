package com.sesac.planet.domain.usecase.main.plan

import com.sesac.planet.data.repository.main.plan.PlanRepository

class DeleteDetailPlanUseCase {
    suspend operator fun invoke(token: String, detailedPlanId: Int) = PlanRepository.deleteDetailPlan(token, detailedPlanId)
}