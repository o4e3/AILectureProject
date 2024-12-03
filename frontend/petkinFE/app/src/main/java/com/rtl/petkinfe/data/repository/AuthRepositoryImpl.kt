package com.rtl.petkinfe.data.repository

import android.util.Log
import com.rtl.petkinfe.data.local.TokenDataSource
import com.rtl.petkinfe.data.mapper.toDomain
import com.rtl.petkinfe.data.remote.api.AuthApi
import com.rtl.petkinfe.data.remote.dto.KakaoTokenRequest
import com.rtl.petkinfe.data.remote.dto.TokenRefreshRequest
import com.rtl.petkinfe.domain.model.UserProfile
import com.rtl.petkinfe.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first


class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val tokenDataSource: TokenDataSource
) : AuthRepository {

    override suspend fun sendKakaoToken(oauthAccessToken: String): Boolean {
        Log.d("testt", "Sending Kakao Token: $oauthAccessToken")
        return try {
            val tokenRequest = KakaoTokenRequest(oauthAccessToken)
            val response = authApi.sendKakaoToken(tokenRequest = tokenRequest)
            tokenDataSource.saveToken(response.accessToken, response.refreshToken)
            Log.d("testt", "액세스 토큰: ${response.accessToken}")
            true
        } catch (e: Exception) {
            Log.e("testt", "카카오 토큰 전송 중 예외 발생: ${e.message}", e)
            false
        }
    }


    override suspend fun refreshToken(refreshToken: String): Boolean {
        Log.d("AuthRepositoryImpl", "Refreshing token: $refreshToken")
        return try {
            val response = authApi.refreshToken(TokenRefreshRequest(refreshToken))
            tokenDataSource.saveToken(response.accessToken, response.refreshToken)
            true
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Failed to refresh token: ${e.message}", e)
            false
        }
    }

    override suspend fun getUserProfile(): UserProfile? {
        Log.d("AuthRepositoryImpl", "Fetching user profile")
        return try {
            authApi.getUserProfile().toDomain()
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Failed to fetch user profile: ${e.message}", e)
            null
        }
    }

    override suspend fun getAccessToken(): String? {
        return try {
            tokenDataSource.getAccessToken().first()
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Failed to get access token: ${e.message}", e)
            null
        }
    }

    override suspend fun getRefreshToken(): String? {
        return try {
            tokenDataSource.getRefreshToken().first()
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Failed to get refresh token: ${e.message}", e)
            null
        }
    }


    override suspend fun isLoggedIn(): Boolean {
        return try {
            tokenDataSource.getAccessToken().first() != null
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Failed to check login state: ${e.message}", e)
            false
        }
    }

    override suspend fun clearLoginState() {
        try {
            tokenDataSource.clearToken()
            Log.d("AuthRepositoryImpl", "Cleared login state")
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Failed to clear login state: ${e.message}", e)
        }
    }


}
