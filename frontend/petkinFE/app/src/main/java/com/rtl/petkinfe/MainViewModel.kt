package com.rtl.petkinfe

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.rtl.petkinfe.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            val token = authRepository.getAccessToken().toString()
            initialize(token)
        }
    }
    fun initialize(token: String?) {
        viewModelScope.launch {
            if (!token.isNullOrEmpty()) {
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    _isLoggedIn.value = error == null && tokenInfo != null
                }
            } else {
                _isLoggedIn.value = false
            }
            _isReady.value = true
        }
    }

}