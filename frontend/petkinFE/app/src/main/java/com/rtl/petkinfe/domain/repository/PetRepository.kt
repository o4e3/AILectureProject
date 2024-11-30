package com.rtl.petkinfe.domain.repository

import com.rtl.petkinfe.domain.model.Pet

interface PetRepository {
    fun getMyPetList(): List<Pet>
    fun registerPet(pet: Pet): Pet
    fun deletePet(pet: Pet)
    fun updatePet(pet: Pet)
}
