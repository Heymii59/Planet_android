package com.sesac.planet.data.model

import androidx.annotation.Keep

@Keep
data class CreateDiaryRequest(
    val content: String,
    val emotion: String,
    val evaluation: Int,
    val images: List<String>,
    val journey_id: Int
)
