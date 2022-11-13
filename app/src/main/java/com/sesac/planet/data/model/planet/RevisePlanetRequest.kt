package com.sesac.planet.data.model.planet

import com.google.gson.annotations.SerializedName

data class RevisePlanetRequest(
    @SerializedName("planet_name") val planetName: String,
    @SerializedName("planet_intro") val planetIntro: String,
    @SerializedName("color") val color: String
)