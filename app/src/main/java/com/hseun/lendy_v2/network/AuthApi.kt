package com.hseun.lendy_v2.network

import com.hseun.lendy_v2.network.model.auth.IdentificationResponse
import com.hseun.lendy_v2.network.model.auth.LoginRequest
import com.hseun.lendy_v2.network.model.auth.SendMailRequest
import com.hseun.lendy_v2.network.model.auth.SignUpRequest
import com.hseun.lendy_v2.network.model.auth.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

private const val AUTH = "/auth"

interface AuthApi {
    @POST("$AUTH/refresh")
    suspend fun refresh(@Header("Authorization") token: String): Response<TokenResponse>

    @POST("$AUTH/email")
    suspend fun sendMail(@Body request: SendMailRequest): Response<Unit>

    @POST("$AUTH/signup")
    suspend fun signUp(@Body request: SignUpRequest): Response<TokenResponse>

    @GET("$AUTH/identification")
    suspend fun getIdentificationUrl(@Header("Authorization") token: String): Response<IdentificationResponse>

    @POST("$AUTH/login")
    suspend fun login(@Body request: LoginRequest): Response<TokenResponse>
}