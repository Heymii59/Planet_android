package com.sesac.planet.data.repository.main.planet

import com.sesac.planet.data.model.BaseResponse
import com.sesac.planet.data.model.planet.CreateNewPlanetRequest
import com.sesac.planet.network.main.planet.PostPlanetAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object PostPlanetRepository {
    lateinit var createNewPlanetService: PostPlanetAPI

    suspend fun createNewPlanet(token: String, journeyId: Int, createNewPlanetRequest: CreateNewPlanetRequest): Response<BaseResponse>{
        val response: Response<BaseResponse>

        withContext(Dispatchers.IO){
            response = createNewPlanetService.createNewPlanet(token,journeyId, createNewPlanetRequest)
        }

        return response
    }
}