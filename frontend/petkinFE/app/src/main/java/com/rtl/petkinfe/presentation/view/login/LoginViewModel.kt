package com.rtl.petkinfe.presentation.view.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.rtl.petkinfe.domain.repository.AuthRepository
import com.rtl.petkinfe.domain.usecases.AutoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val autoLoginUseCase: AutoLoginUseCase,
    private val authRepository: AuthRepository
) : ViewModel()
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

    fun autoLogin() {
        viewModelScope.launch {
            autoLoginUseCase.execute().collect { token ->
                if (token != null) {
                    _loginState.value = LoginState.Success(token)
                } else {
                    _loginState.value = LoginState.Idle
                }
            }
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            authRepository.saveToken(token)
        }
    }

    sealed class LoginState {
        object Idle : LoginState()
        data class Success(val token: String) : LoginState()
        data class Failure(val error: Throwable) : LoginState()
    }
}