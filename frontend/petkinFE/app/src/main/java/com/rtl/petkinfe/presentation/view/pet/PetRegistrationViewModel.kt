package com.rtl.petkinfe.presentation.view.pet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rtl.petkinfe.domain.usecases.RegisterPetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetRegistrationViewModel @Inject constructor(
    private val registerPetUseCase: RegisterPetUseCase
) : ViewModel() {
    // 반려동물 이름, 나이, 성별, 종, 품종 상태
    private val _petName = MutableStateFlow("")
    val petName: StateFlow<String> = _petName

    private val _petAge = MutableStateFlow("")
    val petAge: StateFlow<String> = _petAge

    private val _petGender = MutableStateFlow("")
    val petGender: StateFlow<String> = _petGender

    private val _petSpecies = MutableStateFlow("")
    val petSpecies: StateFlow<String> = _petSpecies

    private val _petBreed = MutableStateFlow("")
    val petBreed: StateFlow<String> = _petBreed

    // 상태 업데이트 함수
    fun updatePetName(name: String) {
        _petName.value = name
    }

    fun updatePetAge(age: String) {
        _petAge.value = age
    }

    fun updatePetGender(gender: String) {
        _petGender.value = gender
    }

    fun updatePetSpecies(species: String) {
        _petSpecies.value = species
    }

    fun updatePetBreed(breed: String) {
        _petBreed.value = breed
    }

    // 펫 등록 함수
    fun registerPet(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                registerPetUseCase.invoke(
                    petName = petName.value,
                    petAge = petAge.value.toIntOrNull()
                        ?: throw IllegalArgumentException("Invalid age input"),
                    petGender = petGender.value,
                    petSpecies = petSpecies.value,
                    petBreed = petBreed.value
                )
                onSuccess() // 성공 시 호출
            } catch (e: Exception) {
                onError(e.message ?: "등록 중 알 수 없는 오류가 발생했습니다.") // 실패 시 호출
            }
        }
    }
}
