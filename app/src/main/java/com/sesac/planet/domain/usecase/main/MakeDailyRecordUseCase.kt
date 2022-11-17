package com.sesac.planet.domain.usecase.main

import com.sesac.planet.data.model.CreateDiaryRequest
import com.sesac.planet.data.repository.main.dailyrecord.DailyRecordRepository

class MakeDailyRecordUseCase {
    suspend operator fun invoke(request: CreateDiaryRequest, token: String) = DailyRecordRepository.makeDailyRecord(request, token)
}