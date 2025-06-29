package com.hseun.lendy_v2.auth.login.repository

import com.hseun.lendy_v2.network.model.auth.TokenResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<TokenResponse>
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class LoginModule {
    @Binds
    abstract fun bindLoginRepository(
        impl: LoginRepositoryImpl
    ) : LoginRepository
}