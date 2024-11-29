package com.rtl.petkinfe.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun saveToken(token: String)
    fun getToken(): Flow<String?>
    suspend fun clearToken()
}