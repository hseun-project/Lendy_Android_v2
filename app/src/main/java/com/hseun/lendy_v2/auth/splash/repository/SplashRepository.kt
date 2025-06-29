package com.hseun.lendy_v2.auth.splash.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface SplashRepository {
    suspend fun autoLogin(): Boolean
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class SplashModule {
    @Binds
    abstract fun bindSplashRepository(
        impl: SplashRepositoryImpl
    ) : SplashRepository
}