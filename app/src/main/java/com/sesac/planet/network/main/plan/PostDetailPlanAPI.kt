package com.sesac.planet.network.main.plan

import com.sesac.planet.data.model.plan.PatchPlanResponse
import com.sesac.planet.data.model.plan.PostDetailPlanRequest
import com.sesac.planet.data.model.plan.PostDetailPlanResponse
import retrofit2.Response
import retrofit2.http.*

interface PostDetailPlanAPI {
    @POST("/plans/{journey_id}/{planet_id}")
    suspend fun postDetailPlan(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("journey_id") journeyId: Int,
        @Path("planet_id") planetId: Int,
        @Body params: PostDetailPlanRequest
    ): Response<PostDetailPlanResponse>

    @PATCH("/plans/{detailed_plan_id}")
    suspend fun patchDetailPlan(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("detailed_plan_id") detailedPlanId: Int,
    ): Response<PatchPlanResponse>
}