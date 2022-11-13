package com.sesac.planet.network.main.planet

import com.sesac.planet.data.model.planet.PlanetDetailInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PlanetDetailAPI {
    @GET("/planets/detail/{planet_id}")
    suspend fun getPlanetDetailInfo(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("planet_id") planetId: Int
    ): Response<PlanetDetailInfoResponse>
}