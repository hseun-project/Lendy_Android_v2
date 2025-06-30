package com.hseun.lendy_v2.main.bank.repository

import com.hseun.lendy_v2.network.UserApi
import com.hseun.lendy_v2.network.model.user.ModifyBankRequest
import com.hseun.lendy_v2.utils.Token
import com.hseun.lendy_v2.utils.apiCall
import javax.inject.Inject

class BankRepositoryImpl @Inject constructor(
    private val api: UserApi,
    private val token: Token
) : BankRepository {
    private val accessToken = token.getAccessToken()

    override suspend fun modifyBank(bankName: String, bankNumber: String): Result<Unit> {
        if (accessToken.isNullOrEmpty()) {
            return Result.failure(Error("Token is not found"))
        }
        return apiCall("modifyBank") { api.modifyBank(accessToken, ModifyBankRequest(bankName, bankNumber)) }
    }
}