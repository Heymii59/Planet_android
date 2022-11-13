package com.sesac.planet.data.repository.main.planet

import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.network.main.planet.DeletePlanetAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object DeletePlanetRepository {
    lateinit var deletePlanetService: DeletePlanetAPI

    suspend fun deletePlanet(token: String, planetId: Int): Response<BaseResponse> {
        val response: Response<BaseResponse>

        withContext(Dispatchers.IO){
            response = deletePlanetService.deletePlanet(token, planetId)
        }

        return response
    }
}