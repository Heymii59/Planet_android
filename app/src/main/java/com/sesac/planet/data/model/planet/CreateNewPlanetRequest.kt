package com.sesac.planet.data.model.planet

import com.google.gson.annotations.SerializedName

data class CreateNewPlanetRequest(
    @SerializedName("planet_name") val planetName: String,
    @SerializedName("planet_intro") val planetIntro: String,
    @SerializedName("color") val color: String,
    @SerializedName("plan_list") val planList: MutableList<CreateNewPlanetPlanListRequest>
)

data class CreateNewPlanetPlanListRequest(
    @SerializedName("plan_content") val planContent: String,
    @SerializedName("type") val type: String
)
