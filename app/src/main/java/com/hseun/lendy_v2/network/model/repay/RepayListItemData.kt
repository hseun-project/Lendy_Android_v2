package com.hseun.lendy_v2.network.model.repay

import com.hseun.lendy_v2.utils.DuringType
import java.util.Date

data class RepayListItemData(
    val id: Long,
    val money: Int,
    val duringType: DuringType,
    val during: Int,
    val startDate: Date,
    val repayment: Int
)
