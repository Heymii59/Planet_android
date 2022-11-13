package com.sesac.planet.network.main.planet

import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.data.model.planet.RevisePlanetRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface RevisePlanetAPI {
    @PATCH("/planets/revise/{planet_id}")
    suspend fun revisePlanet(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("planet_id") planetId: Int,
        @Body params: RevisePlanetRequest
    ): Response<BaseResponse>
}