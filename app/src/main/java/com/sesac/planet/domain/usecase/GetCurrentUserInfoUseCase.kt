package com.sesac.planet.domain.usecase

import com.sesac.planet.data.repository.main.mypage.MyPageRepository

class GetCurrentUserInfoUseCase {
    suspend operator fun invoke(token: String, userIdx: Int) = MyPageRepository.getCurrentUserInfo(token, userIdx)
}