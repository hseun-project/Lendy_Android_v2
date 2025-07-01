package com.hseun.lendy_v2.main.request.detail.repository

import com.hseun.lendy_v2.network.model.loan.RequestDetailResponse
import com.hseun.lendy_v2.utils.ApplyState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface RequestDetailRepository {
    suspend fun getDetailInfo(id: Long): Result<RequestDetailResponse>
    suspend fun changeRequestState(id: Long, state: ApplyState): Result<Unit>
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class RequestDetailModule {
    @Binds
    abstract fun bindRequestDetailRepository(
        impl: RequestDetailRepositoryImpl
    ) : RequestDetailRepository
}