package com.sesac.planet.network.main.mypage

import com.sesac.planet.data.model.mypage.CurrentUserInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MyPageAPI {
    @GET("/users/{user_id}")
    suspend fun getCurrentUserInfo(
        @Header("X-ACCESS-TOKEN") token: String,
        @Path("user_id") userIdx: Int
    ): Response<CurrentUserInfoResponse>
}