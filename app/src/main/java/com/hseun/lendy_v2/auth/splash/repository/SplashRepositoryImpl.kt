package com.hseun.lendy_v2.auth.splash.repository

import com.hseun.lendy_v2.network.AuthApi
import com.hseun.lendy_v2.utils.Token
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashRepositoryImpl @Inject constructor (
    private val api: AuthApi,
    private val token: Token
) : SplashRepository {
    override suspend fun autoLogin(): Boolean {
        val refreshToken = token.getRefreshToken() ?: return false
        return try {
            val response = api.refresh(refreshToken)
            if (response.isSuccessful) {
                response.body()?.let {
                    token.saveToken(it.accessToken, it.refreshToken)
                    true
                } ?: false
            } else {
                token.clearToken()
                false
            }
        } catch (e: Exception) {
            token.clearToken()
            false
        }
    }
}