package com.sesac.planet.network.main.dailyrecord

import com.sesac.planet.data.model.CreateDiaryRequest
import com.sesac.planet.data.model.CreateDiaryResponse
import com.sesac.planet.data.model.QueryDailyRecordRequest
import com.sesac.planet.data.model.QueryDailyRecordResponse
import retrofit2.Response
import retrofit2.http.*

interface DailyRecordAPI {
    @Headers("Content-Type: multipart/form-data; boundary=----WebKitFormBoundarybYjYir4Bcjac10A0")
    @POST("/diary")
    suspend fun makeDailyRecord(
        @Header("X-ACCESS-TOKEN") token: String,
        @Body request: CreateDiaryRequest
    ): Response<CreateDiaryResponse>

    @GET("/diary/date/{user_id}")
    suspend fun queryDailyRecord(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("user_id") userId: Int,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Response<QueryDailyRecordResponse>
}