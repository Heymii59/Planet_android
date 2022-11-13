package com.sesac.planet.network.main.planet

import com.sesac.planet.data.model.BaseResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface DeletePlanetAPI {
    @PATCH("/planets/delete/{planet_id}")
    suspend fun deletePlanet(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("planet_id") planetId: Int
    ): Response<BaseResponse>
}