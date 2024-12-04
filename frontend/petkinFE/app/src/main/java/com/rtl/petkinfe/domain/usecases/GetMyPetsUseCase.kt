package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.Pet
import com.rtl.petkinfe.domain.repository.PetRepository
import javax.inject.Inject

class GetMyPetsUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(): Pet {
        val petList = petRepository.getMyPetList()
        // MVP 에서는 가장 최신의 펫만 가져온다.
        val latestPet = petList.last()
        latestPet.id?.let { petRepository.savePetId(it) } // Long 타입 ID 저장
        return latestPet
    }
}
