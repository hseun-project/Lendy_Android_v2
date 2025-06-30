package com.hseun.lendy_v2.main.home.repository

import com.hseun.lendy_v2.network.model.loan.LentListItemData
import com.hseun.lendy_v2.network.model.loan.RequestListItemData
import com.hseun.lendy_v2.network.model.repay.RepayListItemData
import com.hseun.lendy_v2.network.model.user.UserInfoResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface HomeRepository {
    suspend fun getUserInfo(): Result<UserInfoResponse>
    suspend fun getRequestList(): Result<List<RequestListItemData>>
    suspend fun getLentList(): Result<List<LentListItemData>>
    suspend fun getRepayList(): Result<List<RepayListItemData>>
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun bindHomeRepository(
        impl: HomeRepositoryImpl
    ) : HomeRepository
}