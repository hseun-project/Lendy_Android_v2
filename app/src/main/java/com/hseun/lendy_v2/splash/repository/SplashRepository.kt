package com.hseun.lendy_v2.splash.repository

interface SplashRepository {
    suspend fun autoLogin(): Boolean
}