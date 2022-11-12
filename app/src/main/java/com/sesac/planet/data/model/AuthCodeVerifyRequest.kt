package com.sesac.planet.data.model

data class AuthCodeVerifyRequest(
    val auth: String,
    val email: String
)