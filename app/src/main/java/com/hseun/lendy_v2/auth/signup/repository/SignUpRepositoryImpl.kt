package com.hseun.lendy_v2.auth.signup.repository

import com.hseun.lendy_v2.network.AuthApi
import com.hseun.lendy_v2.network.model.auth.SendMailRequest
import com.hseun.lendy_v2.network.model.auth.SignUpRequest
import com.hseun.lendy_v2.network.model.auth.TokenResponse
import com.hseun.lendy_v2.utils.Token
import com.hseun.lendy_v2.utils.apiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val token: Token
) : SignUpRepository {
    override suspend fun sendMail(email: String): Result<Unit> {
        return apiCall("sendMail") { api.sendMail(SendMailRequest(email)) }
    }

    override suspend fun signUp(
        email: String,
        code: String,
        password: String
    ): Result<TokenResponse> {
        return apiCall("signUp") { api.signUp(SignUpRequest(email, code, password)) }
            .onSuccess { token.saveToken(it.accessToken, it.refreshToken) }
    }
}