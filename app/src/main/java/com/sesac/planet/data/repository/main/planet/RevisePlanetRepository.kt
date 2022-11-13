package com.sesac.planet.data.repository.main.planet

import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.data.model.planet.RevisePlanetRequest
import com.sesac.planet.network.main.planet.RevisePlanetAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object RevisePlanetRepository {
    lateinit var revisePlanetService: RevisePlanetAPI

    suspend fun revisePlanet(token: String, planetId: Int, revisePlanetRequest: RevisePlanetRequest): Response<BaseResponse>{
        val response: Response<BaseResponse>

        withContext(Dispatchers.IO){
            response = revisePlanetService.revisePlanet(token, planetId, revisePlanetRequest)
        }

        return response
    }
}