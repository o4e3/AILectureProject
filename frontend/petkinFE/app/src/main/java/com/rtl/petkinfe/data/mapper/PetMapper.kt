package com.rtl.petkinfe.data.mapper

import com.rtl.petkinfe.data.remote.dto.MyPetsResponseDto
import com.rtl.petkinfe.data.remote.dto.PetRegisterRequestDto
import com.rtl.petkinfe.data.remote.dto.PetRegisterResponseDto
import com.rtl.petkinfe.data.remote.dto.PetResponseDto
import com.rtl.petkinfe.domain.model.Pet

fun PetRegisterResponseDto.toDomainModel(): Pet {
    return Pet(
        id = petId,
        name = name,
        species = species,
        breed = breed,
        age = age,
        gender = gender,
        registerDate = registrationDate
    )
}

fun PetResponseDto.toDomainModel(): Pet {
    return Pet(
        id = petId,
        name = name,
        species = species,
        breed = breed,
        age = age,
        gender = gender,
        registerDate = registrationDate
    )
}

fun MyPetsResponseDto.toDomainModel(): List<Pet> {
    return pets.map { it.toDomainModel() }
}

fun Pet.toPetRegisterRequestDto(): PetRegisterRequestDto {
    return PetRegisterRequestDto(
        name = name,
        species = species,
        breed = breed,
        age = age,
        gender = gender
    );
}

