package com.hseun.lendy_v2.network.model.loan

import com.hseun.lendy_v2.utils.ApplyLoan
import com.hseun.lendy_v2.utils.DuringType

data class ApplyLoanRequest(
    val loanType: ApplyLoan,
    val money: Int,
    val interest: String,
    val duringType: DuringType,
    val during: Int,
    val bondId: Long?
)
