package com.sesac.planet.network.main.plan

import com.sesac.planet.data.model.BaseResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface DeleteDetailPlanAPI {
    @PATCH("/plans/delete/{detailed_plan_id}")
    suspend fun deleteDetailPlan(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("detailed_plan_id") detailedPlanId: Int
    ) : Response<BaseResponse>
}