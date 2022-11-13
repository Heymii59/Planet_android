package com.sesac.planet.network.main.plan

import com.sesac.planet.data.model.CheckNickNameResponse
import com.sesac.planet.data.model.MakeJourneyRequest
import com.sesac.planet.data.model.MakeJourneyResponse
import com.sesac.planet.data.model.plan.TodayGrowthPlansResponse
import retrofit2.Response
import retrofit2.http.*

interface PlanAPI {
    //홈화면에 오늘의 성장계획 리스트를 가져오는 api
    @GET("/plans/today/{journey_id}")
    suspend fun getTodayGrowthPlans(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("journey_id") journeyId: Int
    ): Response<TodayGrowthPlansResponse>

    @POST("/journey/{userId}")
    suspend fun makeJourney(
        @Path("userId") userId: Int,
        @Header("X-ACCESS-TOKEN") token: String,
        @Body journeyRequest: MakeJourneyRequest
    ): Response<MakeJourneyResponse>

    @POST("/journey/nickname")
    suspend fun checkNickName(
        @Body nickName: String
    ): Response<CheckNickNameResponse>
}