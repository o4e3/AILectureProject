package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.Pet
import com.rtl.petkinfe.domain.repository.PetRepository
import javax.inject.Inject

class RegisterPetUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(pet: Pet): Pet {
        return petRepository.registerPet(pet)
    }
}