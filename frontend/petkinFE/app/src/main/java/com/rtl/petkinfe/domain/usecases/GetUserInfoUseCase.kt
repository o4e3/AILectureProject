package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.UserProfile
import com.rtl.petkinfe.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): UserProfile? {
        return authRepository.getUserProfile()?.let {
            UserProfile(
                id = it.id,
                nickname = it.nickname,
                email = it.email
            )
        }
    }
}