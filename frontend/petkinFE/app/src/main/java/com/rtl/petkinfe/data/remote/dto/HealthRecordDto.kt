package com.rtl.petkinfe.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HealthRecordResponse(
    @SerializedName("record_id")
    val recordId: Long,
    @SerializedName("pet_id")
    val itemId: Int,
    val memo: String,
    val timestamp: String
)

data class HealthRecordByMonthResponse(
    val recordId: Long,
    val itemId: Int,
    val date: String // ISO 8601 형식 (예: YYYY-MM-DD)
)


data class ErrorResponse(
    val detail: String
)

data class UpdateHealthRecordRequest(
    val itemId: Int,
    val memo: String,
    val timestamp: String
)


data class CreateHealthRecordRequest(
    @SerializedName("pet_id")
    val petId: Long,
    @SerializedName("item_id")
    val itemId: Int,
    val memo: String,
    val timestamp: String // ISO 8601 형식 (예: 2024-11-21T15:30:00)
)

// Response DTO for creating a health record
data class CreateHealthRecordResponse(
    val recordId: Long
)
