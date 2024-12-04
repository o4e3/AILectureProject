package com.rtl.petkinfe.domain.repository

import com.rtl.petkinfe.domain.model.Pet

interface PetRepository {
    suspend fun getMyPetList(): List<Pet>
    suspend fun registerPet(pet: Pet): Pet
    suspend fun deletePet(petId: Long)
    suspend fun updatePet(pet: Pet)
    suspend fun getPetById(petId: Long): Pet
    suspend fun savePetId(petId: Long)
    suspend fun getSavedPetId(): Long?
}
