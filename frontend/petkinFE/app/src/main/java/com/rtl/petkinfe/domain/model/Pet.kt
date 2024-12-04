package com.rtl.petkinfe.domain.model

import java.util.Date

data class Pet(
    val id: Long?,
    val name: String,
    val species: String,
    val breed: String,
    val age: Int,
    val gender: String,
    val registerDate: String?
)

