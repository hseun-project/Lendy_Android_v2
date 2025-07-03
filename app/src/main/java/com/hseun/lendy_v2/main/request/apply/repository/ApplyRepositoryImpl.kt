package com.hseun.lendy_v2.main.request.apply.repository

import com.hseun.lendy_v2.network.LoanApi
import com.hseun.lendy_v2.network.model.loan.ApplyLoanRequest
import com.hseun.lendy_v2.network.model.loan.BondListItemData
import com.hseun.lendy_v2.utils.ApplyLoan
import com.hseun.lendy_v2.utils.DuringType
import com.hseun.lendy_v2.utils.Token
import com.hseun.lendy_v2.utils.apiCall
import javax.inject.Inject

class ApplyRepositoryImpl @Inject constructor(
    private val api: LoanApi,
    private val token: Token
) : ApplyRepository {
    private val accessToken = token.getAccessToken()

    override suspend fun getBondList(keyword: String): Result<List<BondListItemData>> {
        if (accessToken.isNullOrEmpty()) {
            return Result.failure(Error("Token is not found"))
        }
        return apiCall("getBondList") { api.getBondList(accessToken, keyword) }
    }

    override suspend fun applyLoan(
        loanType: ApplyLoan,
        money: Int,
        interest: String,
        duringType: DuringType,
        during: Int,
        bondId: Long?
    ): Result<Unit> {
        if (accessToken.isNullOrEmpty()) {
            return Result.failure(Error("Token is not found"))
        }
        return apiCall("applyLoan") {
            api.applyLoan(
                accessToken,
                ApplyLoanRequest(loanType, money, interest, duringType, during, bondId)
            )
        }
    }
}