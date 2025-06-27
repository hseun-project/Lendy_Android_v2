package com.hseun.lendy_v2.auth.splash.repository

import com.hseun.lendy_v2.network.AuthApi
import com.hseun.lendy_v2.utils.Token
import com.hseun.lendy_v2.utils.apiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashRepositoryImpl @Inject constructor (
    private val api: AuthApi,
    private val token: Token
) : SplashRepository {
    override suspend fun autoLogin(): Boolean {
        val refreshToken = token.getRefreshToken() ?: return false
        apiCall("splash") { api.refresh(refreshToken) }
            .onSuccess {
                token.saveToken(it.accessToken, it.refreshToken)
                return true
            }
            .onFailure {
                token.clearToken()
                return false
            }
        return false
    }
}