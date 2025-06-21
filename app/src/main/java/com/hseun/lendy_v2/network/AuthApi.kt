package com.hseun.lendy_v2.network

import com.hseun.lendy_v2.network.model.auth.TokenResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/refresh")
    suspend fun refresh(@Header("Authorization") token: String): Response<TokenResponse>
}