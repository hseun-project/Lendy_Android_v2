package com.hseun.lendy_v2.network.model.loan

import com.hseun.lendy_v2.utils.DuringType

data class RequestDetailResponse(
    val id: Long,
    val debtName: String,
    val money: Int,
    val interest: String,
    val duringType: DuringType,
    val during: Int
)
