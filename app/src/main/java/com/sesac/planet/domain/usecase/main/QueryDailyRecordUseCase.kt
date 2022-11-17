package com.sesac.planet.domain.usecase.main

import com.sesac.planet.data.model.QueryDailyRecordRequest
import com.sesac.planet.data.repository.main.dailyrecord.DailyRecordRepository

class QueryDailyRecordUseCase {
    suspend operator fun invoke(startDate: String, endDate: String, token: String, userId: Int) =
        DailyRecordRepository.queryDailyRecord(startDate, endDate, token, userId)
}