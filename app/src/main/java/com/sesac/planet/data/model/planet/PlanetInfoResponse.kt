package com.sesac.planet.data.model.planet

import com.google.gson.annotations.SerializedName
import com.sesac.planet.data.model.BaseResponse

data class PlanetInfoResponse(
    @SerializedName("result") val result: List<ResultPlanetInfo>
) : BaseResponse()

data class ResultPlanetInfo(
    @SerializedName("planet_id") val planet_id: Int,
    @SerializedName("planet_name") val planet_name: String,
    @SerializedName("planet_intro") val planet_intro: String?,
    @SerializedName("planet_exp") val planet_exp: Int,
    @SerializedName("planet_level") val planet_level: Int,
    @SerializedName("plan_count") val plan_count: Int,
    @SerializedName("color_rgb") val color: String?
)

data class PlanetDetailInfoResponse(
    @SerializedName("result") val result: ResultPlanetDetailInfo
): BaseResponse()

data class ResultPlanetDetailInfo(
    @SerializedName("planet_id") val planet_id: Int,
    @SerializedName("planet_name") val planet_name: String,
    @SerializedName("planet_intro") val planet_intro: String?,
    @SerializedName("planet_exp") val planet_exp: Int,
    @SerializedName("planet_level") val planet_level: Int,
    @SerializedName("planet_image") val planet_image: String?,
    @SerializedName("color") val color: String?,
    @SerializedName("plans") val plans: ArrayList<ResultPlanetDetailPlan>
)

data class ResultPlanetDetailPlan(
    @SerializedName("detailed_plan_id") val detailed_plan_id: Int,
    @SerializedName("plan_name") val plan_name: String,
    @SerializedName("type") val type: String,
    @SerializedName("status") val status: Int,
    @SerializedName("is_completed") val is_completed: Int
)