package com.sesac.planet.data.model


import com.google.gson.annotations.SerializedName

data class CheckNickNameResponse(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("isSuccess")
    var isSuccess: Boolean?,
    @SerializedName("message")
    var message: String?
)