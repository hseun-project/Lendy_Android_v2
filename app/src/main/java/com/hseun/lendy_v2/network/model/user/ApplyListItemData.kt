package com.hseun.lendy_v2.network.model.user

import com.hseun.lendy_v2.utils.ApplyLoan
import com.hseun.lendy_v2.utils.ApplyState
import com.hseun.lendy_v2.utils.DuringType

data class ApplyListItemData(
    val id: Long,
    val loanType: ApplyLoan,
    val money: Int,
    val interest: String,
    val duringType: DuringType,
    val during: Int,
    val bondName: String?,
    val bondEmail: String?,
    val state: ApplyState
)
