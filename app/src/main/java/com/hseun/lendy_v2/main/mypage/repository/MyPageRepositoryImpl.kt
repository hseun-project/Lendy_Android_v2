package com.hseun.lendy_v2.main.mypage.repository

import com.hseun.lendy_v2.network.AuthApi
import com.hseun.lendy_v2.network.UserApi
import com.hseun.lendy_v2.network.model.user.ApplyListItemData
import com.hseun.lendy_v2.network.model.user.UserInfoResponse
import com.hseun.lendy_v2.utils.Token
import com.hseun.lendy_v2.utils.apiCall
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val authApi: AuthApi,
    private val token: Token
) : MyPageRepository {
    private val accessToken = token.getAccessToken()

    override suspend fun getUserInfo(): Result<UserInfoResponse> {
        if (accessToken.isNullOrEmpty()) {
            return Result.failure(Error("Token is not found"))
        }
        return apiCall("getUserInfo") { userApi.getUserInfo(accessToken) }
    }

    override suspend fun getApplyLoanList(): Result<List<ApplyListItemData>> {
        if (accessToken.isNullOrEmpty()) {
            return Result.failure(Error("Token is not found"))
        }
        return apiCall("getApplyLoanList") { userApi.getApplyList(accessToken) }
    }

    override suspend fun logout(): Result<Unit> {
        if (accessToken.isNullOrEmpty()) {
            return Result.failure(Error("Token is not found"))
        }
        return apiCall("logout") { authApi.logout(accessToken) }
    }
}