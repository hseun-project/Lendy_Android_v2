package com.hseun.lendy_v2.network

import com.hseun.lendy_v2.network.model.loan.ApplyLoanRequest
import com.hseun.lendy_v2.network.model.loan.BondListItemData
import com.hseun.lendy_v2.network.model.loan.ChangeStateRequest
import com.hseun.lendy_v2.network.model.loan.LentListItemData
import com.hseun.lendy_v2.network.model.loan.RequestDetailResponse
import com.hseun.lendy_v2.network.model.loan.RequestListItemData
import com.hseun.lendy_v2.utils.ApplyLoan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("$LOAN/{applyLoanId}")
    suspend fun getRequestDetail(
        @Header("Authorization") token: String,
        @Path("applyLoanId") id: Long
    ): Response<RequestDetailResponse>

    @PATCH("$LOAN/{applyLoanId}")
    suspend fun changeRequestState(
        @Header("Authorization") token: String,
        @Path("applyLoanId") id: Long,
        @Body request: ChangeStateRequest
    ): Response<Unit>

    @POST(LOAN)
    suspend fun applyLoan(
        @Header("Authorization") token: String,
        @Body request: ApplyLoanRequest
    ): Response<Unit>

    @GET("$LOAN/bond")
    suspend fun getBondList(
        @Header("Authorization") token: String,
        @Query("keyword") keyword: String
    ): Response<List<BondListItemData>>
}