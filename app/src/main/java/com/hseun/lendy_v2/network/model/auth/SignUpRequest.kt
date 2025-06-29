package com.hseun.lendy_v2.network.model.auth

data class SignUpRequest(
    val email: String,
    val code: String,
    val password: String
)
