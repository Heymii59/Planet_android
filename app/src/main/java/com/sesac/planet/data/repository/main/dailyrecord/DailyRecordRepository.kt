package com.sesac.planet.data.repository.main.dailyrecord

import com.sesac.planet.data.model.CreateDiaryRequest
import com.sesac.planet.data.model.QueryDailyRecordRequest
import com.sesac.planet.network.main.dailyrecord.DailyRecordAPI

object DailyRecordRepository {
    lateinit var dailyRecordService: DailyRecordAPI

    suspend fun makeDailyRecord(request: CreateDiaryRequest, token: String) = dailyRecordService.makeDailyRecord(token, request)

    suspend fun queryDailyRecord(startDate: String, endDate: String, token: String, userId: Int) = dailyRecordService.queryDailyRecord(token, userId, startDate, endDate)
}