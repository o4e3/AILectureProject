package com.rtl.petkinfe.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class PredictionResponseDto(
    @SerializedName("analysis_id")
    val analysisId: Long,
    @SerializedName("analysis_date")
    val analysisDate: String,
    @SerializedName("predicted_class_label")
    val predictedClassLabel: String,
    val A1: Float,
    val A2: Float,
    val A3: Float,
    val A4: Float,
    val A5: Float,
    val A6: Float,
    val A7: Float
)

data class PredictionDetailResponseDto(
    @SerializedName("record_id")
    val recordId: Long,
    @SerializedName("pet_id")
    val petId: Int,
    @SerializedName("disease_id")
    val diseaseId: Int,
    @SerializedName("analysis_id")
    val analysisId: Long,
    @SerializedName("image_url")
    val imageUrl: String,
    val timestamp: String
)