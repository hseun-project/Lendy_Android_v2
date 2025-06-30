package com.hseun.lendy_v2.main.mypage.repository

import com.hseun.lendy_v2.network.model.user.ApplyListItemData
import com.hseun.lendy_v2.network.model.user.UserInfoResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface MyPageRepository {
    suspend fun getUserInfo(): Result<UserInfoResponse>
    suspend fun getApplyLoanList(): Result<List<ApplyListItemData>>
    suspend fun logout(): Result<Unit>
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class MyPageModule {
    @Binds
    abstract fun bindMyPageRepository(
        impl: MyPageRepositoryImpl
    ) : MyPageRepository
}