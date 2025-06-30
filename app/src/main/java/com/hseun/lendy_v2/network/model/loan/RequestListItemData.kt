package com.hseun.lendy_v2.network.model.loan

import com.hseun.lendy_v2.utils.DuringType

data class RequestListItemData(
    val id: Long,
    val debtName: String,
    val creditScore: Int,
    val money: Int,
    val duringType: DuringType,
    val during: Int
)
