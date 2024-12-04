package com.rtl.petkinfe.di

import android.annotation.SuppressLint
import com.rtl.petkinfe.data.local.TokenDataSource
import com.rtl.petkinfe.data.remote.api.AuthApi
import com.rtl.petkinfe.data.remote.dto.TokenRefreshRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenApi

class TokenAuthenticator @Inject constructor(
    private val dataSource: TokenDataSource,
    @TokenApi private val tokenApiProvider: Provider<AuthApi> // Lazy injection
) : Authenticator {

    @SuppressLint("SuspiciousIndentation")
    override fun authenticate(route: Route?, response: Response): Request? {
        // 이미 재시도했다면 null 반환
        if (response.request.header("Retry-With-New-Token") != null) {
            return null
        }

        return runBlocking {
            val refreshToken = try {
                dataSource.getRefreshTokenImmediately() // 즉시 값 가져오기
            } catch (e: Exception) {
                null
            }


            if (refreshToken.isNullOrEmpty()) {
                // 리프레시 토큰이 없으면 null 반환
                null
            } else {
                try {
                    val tokenResponse = tokenApiProvider.get().refreshToken(
                        TokenRefreshRequest(refreshToken)
                    )

                    // 토큰 저장
                    dataSource.saveToken(
                        tokenResponse.accessToken,
                        tokenResponse.refreshToken
                    )

                    // 새로운 요청 빌드
                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${tokenResponse.accessToken}")
                        .header("Retry-With-New-Token", "true") // 재시도 방지 헤더 추가
                        .build()
                } catch (e: Exception) {
                    // 예외 처리
                    null
                }
            }
        }
    }
}
