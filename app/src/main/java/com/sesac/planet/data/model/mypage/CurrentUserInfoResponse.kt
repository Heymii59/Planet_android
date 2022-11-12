package com.sesac.planet.data.model.mypage

data class CurrentUserInfoResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)