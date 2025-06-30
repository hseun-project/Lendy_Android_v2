package com.hseun.lendy_v2.network.model.user

data class UserInfoResponse(
    val email: String,
    val name: String,
    val creditScore: Int,
    val bank: BankInfo
)

data class BankInfo(
    val bankName: String,
    val bankNumber: String,
    val money: Int
)
