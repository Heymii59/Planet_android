package com.sesac.planet.data.repository.main.plan

import com.sesac.planet.data.model.MakeJourneyRequest
import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.data.model.CheckNickNameResponse
import com.sesac.planet.data.model.plan.PostDetailPlanRequest
import com.sesac.planet.data.model.plan.TodayGrowthPlansResponse
import com.sesac.planet.network.main.plan.DeleteDetailPlanAPI
import com.sesac.planet.network.main.plan.PlanAPI
import com.sesac.planet.network.main.plan.PostDetailPlanAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object PlanRepository {
    lateinit var planService: PlanAPI
    lateinit var postPlanService: PostDetailPlanAPI
    lateinit var deletePlanService: DeleteDetailPlanAPI

    suspend fun getPlan(token: String, journeyId: Int): Response<TodayGrowthPlansResponse>{
        val response: Response<TodayGrowthPlansResponse>

        withContext(Dispatchers.IO){
            response = planService.getTodayGrowthPlans(token, journeyId)
        }

        return response
    }

    suspend fun makeJourney(journeyRequest: MakeJourneyRequest, token: String, userId: Int) = withContext(Dispatchers.IO) {
        planService.makeJourney(userId, token, journeyRequest)
    }

    suspend fun postPlan(token: String, journeyId: Int, planetId: Int, postDetailPlanRequest: PostDetailPlanRequest): Response<BaseResponse>{
        val postPlanResponse: Response<BaseResponse>

        withContext(Dispatchers.IO){
            postPlanResponse = postPlanService.postDetailPlan(token, journeyId, planetId, postDetailPlanRequest)
        }

        return postPlanResponse
    }

    suspend fun patchPlan(token: String, detailedPlanId: Int): Response<BaseResponse>{
        val patchPlanResponse: Response<BaseResponse>

        withContext(Dispatchers.IO){
            patchPlanResponse = postPlanService.patchDetailPlan(token, detailedPlanId)
        }

        return patchPlanResponse
    }

    suspend fun checkDuplicateNickName(nickName: String): Response<CheckNickNameResponse> {
        val checkNickNameResponse: Response<CheckNickNameResponse>
        withContext(Dispatchers.IO) {
            checkNickNameResponse = planService.checkNickName(nickName)
        }
        return checkNickNameResponse
    }

    suspend fun deleteDetailPlan(token: String, detailedPlanId: Int): Response<BaseResponse>{
        val deletePlanResponse: Response<BaseResponse>

        withContext(Dispatchers.IO){
            deletePlanResponse = deletePlanService.deleteDetailPlan(token, detailedPlanId)
        }

        return deletePlanResponse
    }
}