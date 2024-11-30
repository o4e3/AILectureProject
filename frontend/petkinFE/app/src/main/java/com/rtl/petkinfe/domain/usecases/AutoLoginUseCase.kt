package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AutoLoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun execute(): Flow<String?> = flow {
        emit(authRepository.getAccessToken())
    }
}