package com.sesac.planet.data.model

data class EmailSignUpResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)