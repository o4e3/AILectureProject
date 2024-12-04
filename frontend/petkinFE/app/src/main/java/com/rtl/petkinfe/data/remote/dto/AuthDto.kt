package com.rtl.petkinfe.data.remote.dto

import kotlinx.coroutines.flow.Flow

data class KakaoTokenRequest(val oauthAccessToken: String)

data class LoginResponse(val accessToken: String, val refreshToken: String)

data class TokenRefreshRequest(val refreshToken: String?)

data class UserProfileResponse(val id: Long, val nickname: String, val email: String)