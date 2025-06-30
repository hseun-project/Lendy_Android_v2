package com.hseun.lendy_v2.network.model.loan

import com.hseun.lendy_v2.utils.DuringType
import java.util.Date

data class LentListItemData(
    val id: Long,
    val debtName: String,
    val money: Int,
    val duringType: DuringType,
    val during: Int,
    val startDate: Date,
    val repayment: Int
)
