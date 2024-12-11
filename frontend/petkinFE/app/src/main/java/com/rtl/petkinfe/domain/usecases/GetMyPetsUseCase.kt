package com.rtl.petkinfe.domain.usecases

import android.util.Log
import com.rtl.petkinfe.domain.model.Pet
import com.rtl.petkinfe.domain.repository.PetRepository
import javax.inject.Inject

class GetMyPetsUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(): Pet {
        val petList = petRepository.getMyPetList()
        Log.d("펫등록", petList.toString())
        // MVP 에서는 가장 최신의 펫만 가져온다.
        val latestPet = petList.last()
        Log.d("펫등록", "최신 펫: $latestPet")
        latestPet.id?.let { petRepository.savePetId(it) } // Long 타입 ID 저장
        return latestPet
    }
}
