package com.rtl.petkinfe.domain.repository

import com.rtl.petkinfe.domain.model.UserProfile

interface AuthRepository {
    suspend fun sendKakaoToken(oauthAccessToken: String): Boolean
    suspend fun refreshToken(refreshToken: String): Boolean
    suspend fun getUserProfile(): UserProfile?
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun isLoggedIn(): Boolean
    suspend fun clearLoginState()
    suspend fun getOrRefreshAccessToken(): String?
}