package com.hseun.lendy_v2.main.openloan.repository

import com.hseun.lendy_v2.network.LoanApi
import com.hseun.lendy_v2.network.model.loan.RequestListItemData
import com.hseun.lendy_v2.utils.ApplyLoan
import com.hseun.lendy_v2.utils.Token
import com.hseun.lendy_v2.utils.apiCall
import javax.inject.Inject

class OpenLoanRepositoryImpl @Inject constructor(
    private val api: LoanApi,
    private val token: Token
) : OpenLoanRepository {
    private val accessToken = token.getAccessToken()

    override suspend fun getOpenLoanList(): Result<List<RequestListItemData>> {
        if (accessToken.isNullOrEmpty()) {
            return Result.failure(Error("Token is not found"))
        }
        return apiCall("getOpenLoanList") { api.getRequestList(accessToken, ApplyLoan.PUBLIC_LOAN) }
    }
}