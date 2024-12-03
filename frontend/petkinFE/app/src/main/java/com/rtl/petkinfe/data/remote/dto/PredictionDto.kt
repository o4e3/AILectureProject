package com.rtl.petkinfe.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class PredictionDetailResponseDto(
    @SerializedName("model_name")
    val modelName: String,
    val A1: Double,
    val A2: Double,
    val A3: Double,
    val A4: Double,
    val A5: Double,
    val A6: Double,
    val A7: Double,
    @SerializedName("analysis_date")
    val analysisDate: String,
)


data class PredictionResponseDto(
    @SerializedName("record_id")
    val recordId: Long,
    @SerializedName("analysis_id")
    val analysisId: Long,
    @SerializedName("disease_id")
    val diseaseId: Int,
    val timestamp: String
)