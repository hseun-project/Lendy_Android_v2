package com.hseun.lendy_v2.home.repository

import com.hseun.lendy_v2.network.LoanApi
import com.hseun.lendy_v2.network.RepayApi
import com.hseun.lendy_v2.network.UserApi
import com.hseun.lendy_v2.network.model.loan.LentListItemData
import com.hseun.lendy_v2.network.model.loan.RequestListItemData
import com.hseun.lendy_v2.network.model.repay.RepayListItemData
import com.hseun.lendy_v2.network.model.user.UserInfoResponse
import com.hseun.lendy_v2.utils.ApplyLoan
import com.hseun.lendy_v2.utils.Token
import com.hseun.lendy_v2.utils.apiCall
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val loanApi: LoanApi,
    private val repayApi: RepayApi,
    private val token: Token
) : HomeRepository {
    private val accessToken = token.getAccessToken() ?: ""

    override suspend fun getUserInfo(): Result<UserInfoResponse> {
        return apiCall("getUserInfo") { userApi.getUserInfo(accessToken) }
    }

    override suspend fun getRequestList(): Result<List<RequestListItemData>> {
        return apiCall("getRequestList") { loanApi.getRequestList(accessToken, ApplyLoan.PRIVATE_LOAN) }
    }

    override suspend fun getLentList(): Result<List<LentListItemData>> {
        return apiCall("getLentList") { loanApi.getLentList(accessToken) }
    }

    override suspend fun getRepayList(): Result<List<RepayListItemData>> {
        return apiCall("getRepayList") { repayApi.getRepayList(accessToken) }
    }
}