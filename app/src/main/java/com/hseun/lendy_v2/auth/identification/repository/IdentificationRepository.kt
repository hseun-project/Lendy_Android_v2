package com.hseun.lendy_v2.auth.identification.repository

import com.hseun.lendy_v2.network.model.auth.IdentificationResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface IdentificationRepository {
    suspend fun getUrl(): Result<IdentificationResponse>
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class IdentificationModule {
    @Binds
    abstract fun bindIdentificationRepository(
        impl: IdentificationRepositoryImpl
    ) : IdentificationRepository
}