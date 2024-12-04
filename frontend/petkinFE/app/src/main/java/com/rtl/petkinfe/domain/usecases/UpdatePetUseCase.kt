package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.Pet
import com.rtl.petkinfe.domain.repository.PetRepository
import javax.inject.Inject

class UpdatePetUseCase @Inject constructor(
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(pet: Pet){
        return petRepository.updatePet(pet)
    }
}