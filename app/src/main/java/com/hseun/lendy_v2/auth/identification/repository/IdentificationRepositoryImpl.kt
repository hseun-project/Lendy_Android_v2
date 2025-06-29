package com.hseun.lendy_v2.auth.identification.repository

import com.hseun.lendy_v2.network.AuthApi
import com.hseun.lendy_v2.network.model.auth.IdentificationResponse
import com.hseun.lendy_v2.utils.Token
import com.hseun.lendy_v2.utils.apiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IdentificationRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val token: Token
) : IdentificationRepository {
    override suspend fun getUrl(): Result<IdentificationResponse> {
        val accessToken = token.getAccessToken() ?: return Result.failure(Error("Token is empty"))
        return apiCall("getIdentificationUrl") { api.getIdentificationUrl(accessToken) }
    }
}