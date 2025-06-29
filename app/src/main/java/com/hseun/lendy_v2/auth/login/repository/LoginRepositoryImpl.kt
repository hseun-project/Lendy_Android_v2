package com.hseun.lendy_v2.auth.login.repository

import com.hseun.lendy_v2.network.AuthApi
import com.hseun.lendy_v2.network.model.auth.LoginRequest
import com.hseun.lendy_v2.network.model.auth.TokenResponse
import com.hseun.lendy_v2.utils.Token
import com.hseun.lendy_v2.utils.apiCall
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val token: Token
) : LoginRepository {
    override suspend fun login(email: String, password: String): Result<TokenResponse> {
        return apiCall("login") { api.login(LoginRequest(email, password)) }
            .onSuccess { token.saveToken(it.accessToken, it.refreshToken) }
    }
}