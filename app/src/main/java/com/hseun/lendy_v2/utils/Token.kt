package com.hseun.lendy_v2.utils

import com.hseun.lendy_v2.local.TokenPreference
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Token @Inject constructor(
    private val pref: TokenPreference
) {
    fun getRefreshToken(): String? = pref.getRefreshToken()
    fun getAccessToken(): String? = pref.getAccessToken()

    fun saveToken(accessToken: String, refreshToken: String) {
        pref.setAccessToken(accessToken)
        pref.setRefreshToken(refreshToken)
    }

    fun clearToken() {
        pref.clearAccessToken()
        pref.clearRefreshToken()
    }
}