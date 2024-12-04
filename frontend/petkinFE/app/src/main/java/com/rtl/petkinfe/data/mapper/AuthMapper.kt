package com.rtl.petkinfe.data.mapper

import com.rtl.petkinfe.data.remote.dto.UserProfileResponse
import com.rtl.petkinfe.domain.model.UserProfile

fun UserProfileResponse.toDomain(): UserProfile {
    return UserProfile(
        id = id,
        nickname = nickname,
        email = email
    )
}
