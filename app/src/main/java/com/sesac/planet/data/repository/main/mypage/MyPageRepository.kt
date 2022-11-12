package com.sesac.planet.data.repository.main.mypage

import com.sesac.planet.data.model.mypage.CurrentUserInfoResponse
import com.sesac.planet.network.main.mypage.MyPageAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object MyPageRepository {
    lateinit var myPageAPI: MyPageAPI
    suspend fun getCurrentUserInfo(token: String, userIdx: Int): Response<CurrentUserInfoResponse> {
        val response: Response<CurrentUserInfoResponse>
        withContext(Dispatchers.IO) {
            response = myPageAPI.getCurrentUserInfo(token, userIdx)
        }
        return response
    }
}