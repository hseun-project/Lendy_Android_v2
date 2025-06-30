package com.hseun.lendy_v2.network

import com.hseun.lendy_v2.network.model.user.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

private const val USER = "user"

interface UserApi {
    @GET("$USER/info")
    suspend fun getUserInfo(@Header("Authorization") token: String): Response<UserInfoResponse>
}