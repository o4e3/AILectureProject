package com.rtl.petkinfe.data.remote.api

import com.rtl.petkinfe.data.remote.dto.KakaoTokenRequest
import com.rtl.petkinfe.data.remote.dto.LoginResponse
import com.rtl.petkinfe.data.remote.dto.TokenRefreshRequest
import com.rtl.petkinfe.data.remote.dto.UserProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {
    @POST("api/customers/oauth/login/{oauthProvider}")
    suspend fun sendKakaoToken(
        @Path("oauthProvider") oauthProvider: String = "KAKAO",
        @Body tokenRequest: KakaoTokenRequest
    ): LoginResponse

    @POST("api/customers/token/refresh")
    suspend fun refreshToken(
        @Body refreshTokenRequest: TokenRefreshRequest
    ): LoginResponse

    @GET("api/customers/me")
    suspend fun getUserProfile(): UserProfileResponse
}