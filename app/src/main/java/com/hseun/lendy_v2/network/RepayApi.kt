package com.hseun.lendy_v2.network

import com.hseun.lendy_v2.network.model.repay.RepayListItemData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

private const val REPAY = "repay"

interface RepayApi {
    @GET(REPAY)
    suspend fun getRepayList(@Header("Authorization") token: String): Response<List<RepayListItemData>>
}