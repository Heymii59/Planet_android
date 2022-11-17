package com.sesac.planet.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CreateDiaryResponse(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("isSuccess")
    var isSuccess: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("result")
    var result: ResultXX?
)

@Keep
data class ResultXX(
    @SerializedName("content")
    var content: String?,
    @SerializedName("emotion")
    var emotion: String?,
    @SerializedName("evaluation")
    var evaluation: Int?,
    @SerializedName("journey_id")
    var journeyId: Int?
)