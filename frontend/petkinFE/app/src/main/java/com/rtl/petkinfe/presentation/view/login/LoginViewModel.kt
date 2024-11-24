package com.rtl.petkinfe.presentation.view.login

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel()
{
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState
    fun loginWithKakao(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    _loginState.value = LoginState.Failure(error)
                } else if (token != null) {
                    _loginState.value = LoginState.Success(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                if (error != null) {
                    _loginState.value = LoginState.Failure(error)
                } else if (token != null) {
                    _loginState.value = LoginState.Success(token.accessToken)
                }
            }
        }
    }

    sealed class LoginState {
        object Idle : LoginState()
        data class Success(val token: String) : LoginState()
        data class Failure(val error: Throwable) : LoginState()
    }
}