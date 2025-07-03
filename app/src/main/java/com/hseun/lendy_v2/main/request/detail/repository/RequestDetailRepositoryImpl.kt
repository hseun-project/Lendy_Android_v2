package com.hseun.lendy_v2.main.request.detail.repository

import com.hseun.lendy_v2.network.LoanApi
import com.hseun.lendy_v2.network.model.loan.ChangeStateRequest
import com.hseun.lendy_v2.network.model.loan.RequestDetailResponse
import com.hseun.lendy_v2.utils.ApplyState
import com.hseun.lendy_v2.utils.Token
import com.hseun.lendy_v2.utils.apiCall
import javax.inject.Inject

class RequestDetailRepositoryImpl @Inject constructor(
    private val api: LoanApi,
    private val token: Token
) : RequestDetailRepository {
    private val accessToken = token.getAccessToken()

    override suspend fun getDetailInfo(id: Long): Result<RequestDetailResponse> {
        if (accessToken.isNullOrEmpty()) {
            return Result.failure(Error("Token is not found"))
        }
        return apiCall("getDetailInfo") { api.getRequestDetail(accessToken, id)}
    }

    override suspend fun changeRequestState(id: Long, state: ApplyState): Result<Unit> {
        if (accessToken.isNullOrEmpty()) {
            return Result.failure(Error("Token is not found"))
        }
        return apiCall("changeRequestState") { api.changeRequestState(accessToken, id, ChangeStateRequest(state)) }
    }
}