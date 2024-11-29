package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AutoLoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun execute(): Flow<String?> {
        return authRepository.getToken()
    }
}