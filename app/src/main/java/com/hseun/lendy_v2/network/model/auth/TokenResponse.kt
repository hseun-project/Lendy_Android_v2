package com.hseun.lendy_v2.network.model.auth

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)