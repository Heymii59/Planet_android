package com.sesac.planet.data.model

data class EmailSignUpRequest(
    val email: String,
    val password: String,
    val phone_num: String,
    val user_name: String
)