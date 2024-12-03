package com.rtl.petkinfe.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PetRegisterRequestDto(
    val name: String,
    val species: String,
    val breed: String,
    val age: Int,
    val gender: String)

data class PetRegisterResponseDto(
    @SerializedName("pet_id")
    val petId: Long,
    val name: String,
    val species: String,
    @SerializedName("registration_date")
    val registrationDate: String
)

data class PetResponseDto(
    @SerializedName("pet_id")
    val petId: Long,
    val name: String,
    val species: String,
    val breed: String,
    val age: Int,
    val gender: String,
    @SerializedName("registration_date")
    val registrationDate: String
)

data class MyPetsResponseDto(
    val pets: List<PetResponseDto>
)