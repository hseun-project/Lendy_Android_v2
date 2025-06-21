package com.hseun.lendy_v2.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class TokenPreference(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val ACCESS_TOKEN_KEY = "accessToken"
        private const val REFRESH_TOKEN_KEY = "refreshToken"
    }

    fun setAccessToken(token: String) {
        prefs.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }
    fun getAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN_KEY, null)
    }
    fun clearAccessToken() {
        prefs.edit().remove(ACCESS_TOKEN_KEY).apply()
    }

    fun setRefreshToken(token: String) {
        prefs.edit().putString(REFRESH_TOKEN_KEY, token).apply()
    }
    fun getRefreshToken(): String? {
        return prefs.getString(REFRESH_TOKEN_KEY, null)
    }
    fun clearRefreshToken() {
        prefs.edit().remove(REFRESH_TOKEN_KEY).apply()
    }
}
