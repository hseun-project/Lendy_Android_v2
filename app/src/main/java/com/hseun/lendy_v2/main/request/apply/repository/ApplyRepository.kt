package com.hseun.lendy_v2.main.request.apply.repository

import com.hseun.lendy_v2.network.model.loan.BondListItemData
import com.hseun.lendy_v2.utils.ApplyLoan
import com.hseun.lendy_v2.utils.DuringType
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface ApplyRepository {
    suspend fun applyLoan(
        loanType: ApplyLoan,
        money: Int,
        interest: String,
        duringType: DuringType,
        during: Int,
        bondId: Long?
    ): Result<Unit>

    suspend fun getBondList(keyword: String): Result<List<BondListItemData>>
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ApplyModule {
    @Binds
    abstract fun bindApplyRepository(
        impl: ApplyRepositoryImpl
    ) : ApplyRepository
}