package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.Pet
import com.rtl.petkinfe.domain.repository.PetRepository
import javax.inject.Inject

class RegisterPetUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(
        petName: String,
        petAge: Int,
        petGender: String,
        petSpecies: String,
        petBreed: String
    ): Pet {
        // 성별 매핑
        val mappedGender = when (petGender) {
            "수컷" -> "M"
            "암컷" -> "F"
            "중성화 수컷" -> "M"
            "중성화 암컷" -> "F"
            else -> throw IllegalArgumentException("Invalid gender selected.")
        }

        // 종 매핑
        val mappedSpecies = when (petSpecies) {
            "강아지" -> "Dog"
            "고양이" -> "Cat"
            else -> throw IllegalArgumentException("Invalid species selected.")
        }

        // Pet 도메인 모델 생성
        val pet = Pet(
            name = petName,
            age = petAge,
            gender = mappedGender,
            species = mappedSpecies,
            breed = petBreed.ifBlank { "Unknown" } // 품종이 비어있으면 기본값 "Unknown"
        )

        // 리포지토리를 통해 등록
        return petRepository.registerPet(pet)
    }
}