package com.hseun.lendy_v2.auth.signup.repository

import com.hseun.lendy_v2.network.model.auth.TokenResponse

interface SignUpRepository {
    suspend fun sendMail(mail: String): Result<Unit>
    suspend fun signUp(mail: String, code: String, password: String): Result<TokenResponse>
}