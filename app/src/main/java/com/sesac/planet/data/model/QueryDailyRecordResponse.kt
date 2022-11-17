package com.sesac.planet.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class QueryDailyRecordResponse(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("isSuccess")
    var isSuccess: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("result")
    var result: List<ResultXXX>?
)

@Keep
data class ResultXXX(
    @SerializedName("content")
    var content: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("diary_id")
    var diaryId: Int?,
    @SerializedName("emotion")
    var emotion: String?,
    @SerializedName("evaluation")
    var evaluation: Int?,
    @SerializedName("images")
    var images: List<String?>?
)