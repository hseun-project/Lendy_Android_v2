package com.hseun.lendy_v2.main.bank.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface BankRepository {
    suspend fun modifyBank(bankName: String, bankNumber: String): Result<Unit>
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class BankModule {
    @Binds
    abstract fun bindBankRepository(
        impl: BankRepositoryImpl
    ) : BankRepository
}