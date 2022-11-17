package com.sesac.planet.network.main.planet

import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.data.model.planet.CreateNewPlanetRequest
import com.sesac.planet.data.model.planet.CreateNewPlanetResponse
import com.sesac.planet.data.model.planet.CreateNewPlanetResult
import retrofit2.Response
import retrofit2.http.*

interface PostPlanetAPI {
    @POST("/planets/new/{journey_id}")
    suspend fun createNewPlanet(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("journey_id") journeyId: Int,
        @Body params: CreateNewPlanetRequest
    ): Response<CreateNewPlanetResponse>
}