package com.hseun.lendy_v2.network

import com.hseun.lendy_v2.network.model.loan.LentListItemData
import com.hseun.lendy_v2.network.model.loan.RequestListItemData
import com.hseun.lendy_v2.utils.ApplyLoan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private const val LOAN = "loans"

interface LoanApi {
    @GET(LOAN)
    suspend fun getRequestList(
        @Header("Authorization") token: String,
        @Query("loanType") loanType: ApplyLoan
    ): Response<List<RequestListItemData>>

    @GET("$LOAN/lent")
    suspend fun getLentList(@Header("Authorization") token: String): Response<List<LentListItemData>>
}