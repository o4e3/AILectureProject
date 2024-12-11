package com.rtl.petkinfe.data.repository

import android.util.Log
import com.rtl.petkinfe.data.local.SharedPrefManager
import com.rtl.petkinfe.data.mapper.toDomainModel
import com.rtl.petkinfe.data.mapper.toPetRegisterRequestDto
import com.rtl.petkinfe.data.remote.api.PetApi
import com.rtl.petkinfe.domain.model.Pet
import com.rtl.petkinfe.domain.repository.PetRepository
import javax.inject.Inject

class PetRepositoryImpl @Inject constructor(
    private val petApi: PetApi,
    private val sharedPrefManager: SharedPrefManager
): PetRepository {
    override suspend fun getMyPetList(): List<Pet> {
        return try {
            val response = petApi.getMyPets().toDomainModel()
            Log.d("펫등록", response.toString())
            response
        } catch (e: Exception) {
            emptyList() // 기본값 반환
        }
    }
    override suspend fun savePetId(id: Long) = sharedPrefManager.savePetId(id)
    override suspend fun deletePet(id: Long) = petApi.deletePet(id)
    override suspend fun updatePet(pet: Pet) {
        petApi.updatePet(pet.id!!, pet.toPetRegisterRequestDto())
    }
    override suspend fun registerPet(pet: Pet): Pet {
        return petApi.registerPet(pet.toPetRegisterRequestDto()).toDomainModel()
    }

    override suspend fun getPetById(petId: Long) = petApi.getPetById(petId).toDomainModel()
    override suspend fun getSavedPetId() = sharedPrefManager.getPetId()
}