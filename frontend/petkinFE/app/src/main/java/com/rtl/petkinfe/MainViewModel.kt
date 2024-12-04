package com.rtl.petkinfe

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.rtl.petkinfe.domain.repository.AuthRepository
import com.rtl.petkinfe.domain.usecases.GetMyPetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val getMyPetsUseCase: GetMyPetsUseCase
) : ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _petRegistered = MutableStateFlow<Boolean?>(null) // 펫 등록 여부를 관리
    val petRegistered: StateFlow<Boolean?> = _petRegistered.asStateFlow()


    init {
        viewModelScope.launch {
            checkLoginStatus()
        }
    }

    private suspend fun checkLoginStatus() {
        val token = authRepository.getAccessToken().toString()
        if (token.isNullOrEmpty()) {
            _isLoggedIn.value = false
            _isReady.value = true
        } else {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                _isLoggedIn.value = error == null && tokenInfo != null
                _isReady.value = true
            }

            // 로그인 후 펫 등록 여부 체크
            checkPetRegistration()
        }
    }

    private suspend fun checkPetRegistration() {
        try {
            val pet = getMyPetsUseCase.invoke() // 펫 정보 확인
            _petRegistered.value = pet.id != null
        } catch (e: Exception) {
            _petRegistered.value = false
        }
    }

}