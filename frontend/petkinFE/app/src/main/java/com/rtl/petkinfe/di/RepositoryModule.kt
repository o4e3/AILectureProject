package com.rtl.petkinfe.di

import com.rtl.petkinfe.data.local.SharedPrefManager
import com.rtl.petkinfe.data.local.TokenDataSource
import com.rtl.petkinfe.data.remote.api.AuthApi
import com.rtl.petkinfe.data.remote.api.PetApi
import com.rtl.petkinfe.data.repository.AuthRepositoryImpl
import com.rtl.petkinfe.data.repository.HealthRecordRepositoryImpl
import com.rtl.petkinfe.data.repository.PetRepositoryImpl
import com.rtl.petkinfe.data.repository.PredictionRepositoryImpl
import com.rtl.petkinfe.domain.repository.AuthRepository
import com.rtl.petkinfe.domain.repository.HealthRecordRepository
import com.rtl.petkinfe.domain.repository.PetRepository
import com.rtl.petkinfe.domain.repository.PredictionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // PetRepository의 바인딩을 처리하는 @Binds 메서드
    @Binds
    abstract fun bindPetRepository(petRepositoryImpl: PetRepositoryImpl): PetRepository

    @Binds
    abstract fun bindHealthRecordRepository(healthRecordRepositoryImpl: HealthRecordRepositoryImpl): HealthRecordRepository

    @Binds
    abstract fun bindsPredictRepository(predictionRepositoryImpl: PredictionRepositoryImpl): PredictionRepository
}

