package com.rtl.petkinfe.data.remote.api

import com.rtl.petkinfe.data.remote.dto.PetRegisterRequestDto
import com.rtl.petkinfe.data.remote.dto.PetRegisterResponseDto
import com.rtl.petkinfe.data.remote.dto.PetResponseDto
import com.rtl.petkinfe.data.remote.dto.TokenRefreshRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PetApi {
    @POST("api/pets")
    suspend fun registerPet(
        @Body petRegisterRequest: PetRegisterRequestDto
    ) : PetRegisterResponseDto

    @GET("api/pets/{pet_id}")
    suspend fun getPetById(
        @Path ("pet_id") petId: Long
    ): PetResponseDto

    @PUT("api/pets/{pet_id}")
    suspend fun updatePet(
        @Path ("pet_id") petId: Long,
        @Body petRegisterRequest: PetRegisterRequestDto
    ): PetResponseDto

    @DELETE("api/pets/{pet_id}")
    suspend fun deletePet(
        @Path ("pet_id") petId: Long
    )

    @GET("api/pets/mine")
    suspend fun getMyPets() : List<PetResponseDto>
}