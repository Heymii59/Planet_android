package com.sesac.planet.data.model

data class RequestEmailAuthCodeResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: String
)