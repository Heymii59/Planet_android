package com.sesac.planet.network.main.planet

import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.data.model.planet.CreateNewPlanetRequest
import retrofit2.Response
import retrofit2.http.*

interface PostPlanetAPI {
    @POST("/planets/new/{journey_id}")
    suspend fun createNewPlanet(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("journey_id") journeyId: Int,
        @Body params: CreateNewPlanetRequest
    ): Response<BaseResponse>
}