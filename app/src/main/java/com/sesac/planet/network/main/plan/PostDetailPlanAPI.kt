package com.sesac.planet.network.main.plan

import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.data.model.CheckNickNameResponse
import com.sesac.planet.data.model.plan.PostDetailPlanRequest
import retrofit2.Response
import retrofit2.http.*

interface PostDetailPlanAPI {
    @POST("/plans/{journey_id}/{planet_id}")
    suspend fun postDetailPlan(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("journey_id") journeyId: Int,
        @Path("planet_id") planetId: Int,
        @Body params: PostDetailPlanRequest
    ): Response<BaseResponse>

    @PATCH("/plans/{detailed_plan_id}")
    suspend fun patchDetailPlan(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("detailed_plan_id") detailedPlanId: Int,
    ): Response<BaseResponse>


}