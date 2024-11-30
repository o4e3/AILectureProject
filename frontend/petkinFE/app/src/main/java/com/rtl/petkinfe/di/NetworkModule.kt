package com.rtl.petkinfe.di

import com.rtl.petkinfe.BuildConfig
import com.rtl.petkinfe.data.local.TokenDataSource
import com.rtl.petkinfe.data.remote.api.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(
        dataSource: TokenDataSource,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(dataSource))
            .addInterceptor(loggingInterceptor)
            .authenticator(tokenAuthenticator)
            // 연결 타임아웃 설정 (예: 30초)
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            // 읽기 타임아웃 설정 (예: 30초)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            // 쓰기 타임아웃 설정 (예: 30초)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()

    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    @TokenApi
    fun provideTokenApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}