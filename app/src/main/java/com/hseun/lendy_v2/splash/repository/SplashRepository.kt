package com.hseun.lendy_v2.splash.repository

import com.hseun.lendy_v2.local.TokenPreference
import com.hseun.lendy_v2.network.AuthApi

class SplashRepository(
    private val api: AuthApi,
    private val pref: TokenPreference
) {
    private fun getRefreshToken(): String? = pref.getRefreshToken()
    private fun saveToken(accessToken: String, refreshToken: String) {
        pref.setAccessToken("Bearer $accessToken")
        pref.setRefreshToken("Bearer $refreshToken")
    }
    private fun clearToken() {
        pref.clearAccessToken()
        pref.clearRefreshToken()
    }
    suspend fun autoLogin(): Boolean {
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