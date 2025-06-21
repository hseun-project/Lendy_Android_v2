package com.hseun.lendy_v2.splash.repository

import com.hseun.lendy_v2.local.TokenPreference
import com.hseun.lendy_v2.network.AuthApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashRepositoryImpl @Inject constructor (
    private val api: AuthApi,
    private val pref: TokenPreference
) : SplashRepository {
    private fun getRefreshToken(): String? = pref.getRefreshToken()
    private fun saveToken(accessToken: String, refreshToken: String) {
        pref.setAccessToken(accessToken)
        pref.setRefreshToken(refreshToken)
    }
    private fun clearToken() {
        pref.clearAccessToken()
        pref.clearRefreshToken()
    }

    override suspend fun autoLogin(): Boolean {
        val refreshToken = getRefreshToken() ?: return false
        return try {
            val response = api.refresh(refreshToken)
            if (response.isSuccessful) {
                response.body()?.let {
                    saveToken(it.accessToken, it.refreshToken)
                    true
                } ?: false
            } else {
                clearToken()
                false
            }
        } catch (e: Exception) {
            clearToken()
            false
        }
    }
}