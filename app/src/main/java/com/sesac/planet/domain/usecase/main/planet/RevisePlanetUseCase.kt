package com.sesac.planet.domain.usecase.main.planet

import com.sesac.planet.data.model.planet.RevisePlanetRequest
import com.sesac.planet.data.repository.main.planet.RevisePlanetRepository

class RevisePlanetUseCase {
    suspend operator fun invoke(token: String, planetId: Int, revisePlanetRequest: RevisePlanetRequest) = RevisePlanetRepository.revisePlanet(token, planetId, revisePlanetRequest)
}