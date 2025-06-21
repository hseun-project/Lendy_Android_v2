package com.hseun.lendy_v2.auth.splash.repository

interface SplashRepository {
    suspend fun autoLogin(): Boolean
}