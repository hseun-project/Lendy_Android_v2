package com.hseun.lendy_v2.main.openloan.repository

import com.hseun.lendy_v2.network.model.loan.RequestListItemData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface OpenLoanRepository {
    suspend fun getOpenLoanList(): Result<List<RequestListItemData>>
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class OpenLoanModule {
    @Binds
    abstract fun bindOpenLoanRepository(
        impl: OpenLoanRepositoryImpl
    ) : OpenLoanRepository
}