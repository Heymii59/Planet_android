package com.sesac.planet.data.model

import androidx.annotation.Keep

@Keep
data class QueryDailyRecordRequest(
    val end_date: String,
    val start_date: String
)