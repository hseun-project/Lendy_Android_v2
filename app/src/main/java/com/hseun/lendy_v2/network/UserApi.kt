package com.hseun.lendy_v2.network

import com.hseun.lendy_v2.network.model.user.ApplyListItemData
import com.hseun.lendy_v2.network.model.user.ModifyBankRequest
import com.hseun.lendy_v2.network.model.user.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

private const val USER = "user"

interface UserApi {
    @GET("$USER/info")
    suspend fun getUserInfo(@Header("Authorization") token: String): Response<UserInfoResponse>

    @GET("$USER/apply")
    suspend fun getApplyList(@Header("Authorization") token: String): Response<List<ApplyListItemData>>

    @PATCH("$USER/bank")
    suspend fun modifyBank(
        @Header("Authorization") token: String,
        @Body request: ModifyBankRequest
    ): Response<Unit>
}