package com.sesac.planet.data.model

data class EmailSignInResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ResultX
)