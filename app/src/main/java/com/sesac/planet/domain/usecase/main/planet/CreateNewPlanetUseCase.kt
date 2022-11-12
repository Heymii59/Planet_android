package com.sesac.planet.domain.usecase.main.planet

import com.sesac.planet.data.model.planet.CreateNewPlanetRequest
import com.sesac.planet.data.repository.main.planet.PostPlanetRepository

class CreateNewPlanetUseCase {
    suspend operator fun invoke(token: String, journeyId: Int, createNewPlanetRequest: CreateNewPlanetRequest) = PostPlanetRepository.createNewPlanet(token, journeyId, createNewPlanetRequest)
}