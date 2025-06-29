package com.hseun.lendy_v2.auth.signup.repository

import com.hseun.lendy_v2.network.model.auth.TokenResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

interface SignUpRepository {
    suspend fun sendMail(email: String): Result<Unit>
    suspend fun signUp(email: String, code: String, password: String): Result<TokenResponse>
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class SignUpModule {
    @Binds
    abstract fun bindSignUpRepository(
        impl: SignUpRepositoryImpl
    ): SignUpRepository
}