package com.sesac.planet.data.model.plan

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.sesac.planet.data.model.BaseResponse

@Keep
data class PatchPlanResponse (
    @SerializedName("result") val result: PatchPlanResult
): BaseResponse()

@Keep
data class PatchPlanResult (
    @SerializedName("color") val color: String,
    @SerializedName("detailed_plan_id") val detailed_plan_id: Int,
    @SerializedName("is_completed") val is_completed: Int,
    @SerializedName("plan_content") val plan_content: String,
    @SerializedName("plan_exp") val plan_exp: Int,
    @SerializedName("plan_id") val plan_id: Int,
    @SerializedName("planet_level") val planet_level: Int,
    @SerializedName("type") val type: String
)