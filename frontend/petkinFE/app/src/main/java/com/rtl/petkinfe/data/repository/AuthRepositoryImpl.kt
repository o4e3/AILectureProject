package com.rtl.petkinfe.data.repository

import com.rtl.petkinfe.data.local.TokenDataSource
import com.rtl.petkinfe.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow


class AuthRepositoryImpl(private val tokenDataSource: TokenDataSource) : AuthRepository {
    override suspend fun saveToken(token: String) {
        tokenDataSource.saveToken(token)
    }

    override fun getToken(): Flow<String?> {
        return tokenDataSource.getToken()
    }

    override suspend fun clearToken() {
        tokenDataSource.clearToken()
    }
}
