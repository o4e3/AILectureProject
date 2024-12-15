package com.rtl.petkinfe.di

import com.rtl.petkinfe.data.local.TokenDataSource
import com.rtl.petkinfe.data.local.dao.PhotoDao
import com.rtl.petkinfe.data.remote.api.AuthApi
import com.rtl.petkinfe.data.remote.api.PredictionApi
import com.rtl.petkinfe.data.repository.AuthRepositoryImpl
import com.rtl.petkinfe.data.repository.PredictionRepositoryImpl
import com.rtl.petkinfe.domain.repository.AuthRepository
import com.rtl.petkinfe.domain.repository.PredictionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvidesModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi,
        tokenDataSource: TokenDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(authApi, tokenDataSource)
    }

}
