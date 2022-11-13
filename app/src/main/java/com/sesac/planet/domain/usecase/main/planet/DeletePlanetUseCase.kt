package com.sesac.planet.domain.usecase.main.planet

import com.sesac.planet.data.repository.main.planet.DeletePlanetRepository

class DeletePlanetUseCase {
    suspend operator fun invoke(token: String, planetId: Int) = DeletePlanetRepository.deletePlanet(token, planetId)
}