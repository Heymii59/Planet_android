package com.sesac.planet.data.model.planet

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sesac.planet.data.model.BaseResponse

@Keep
data class CreateNewPlanetResponse(
    @SerializedName("result") val result: CreateNewPlanetResult
): BaseResponse()

@Keep
data class CreateNewPlanetResult(
    @SerializedName("color") val color: String,
    @SerializedName("planet_id") val planet_id: Int,
    @SerializedName("planet_intro") val planet_intro: String,
    @SerializedName("planet_name") val planet_name: String
)
