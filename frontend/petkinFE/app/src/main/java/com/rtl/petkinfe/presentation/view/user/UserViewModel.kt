package com.rtl.petkinfe.presentation.view.user


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rtl.petkinfe.domain.usecases.GetMyPetsUseCase
import com.rtl.petkinfe.domain.model.Pet
import com.rtl.petkinfe.domain.model.UserProfile
import com.rtl.petkinfe.domain.usecases.GetUserInfoUseCase
import com.rtl.petkinfe.domain.usecases.UpdatePetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getMyPetsUseCase: GetMyPetsUseCase,
    private val updatePetUseCase: UpdatePetUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _latestPet = MutableStateFlow<Pet?>(null)
    val latestPet: StateFlow<Pet?> = _latestPet

    // 사용자 정보 관리
    private val _userInfo = MutableStateFlow(UserProfile(id=0L, nickname = "Unknown", email = "Unknown"))
    val userInfo: StateFlow<UserProfile> = _userInfo

    init {
        loadLatestPet()
        loadUserInfo()
    }

    private fun loadLatestPet() {
        viewModelScope.launch {
            try {
                val pet = getMyPetsUseCase.invoke()
                _latestPet.value = pet
            } catch (e: Exception) {
                _latestPet.value = null
            }
        }
    }

    fun updatePet(pet: Pet) {
        viewModelScope.launch {
            try {
                updatePetUseCase.invoke(pet)
                _latestPet.value = pet
            } catch (e: Exception) {
                _latestPet.value = null
            }
        }
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            try {
                val userInfo = getUserInfoUseCase.invoke()
                if (userInfo != null) {
                    _userInfo.value = userInfo
                }
            } catch (e: Exception) {
                _userInfo.value = UserProfile(id = 0L, nickname = "Unknown", email = "Unknown")
            }
        }
    }
}
