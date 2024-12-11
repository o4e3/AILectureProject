package com.rtl.petkinfe.domain.model

import com.google.gson.annotations.SerializedName

data class Prediction(
    val analysisId: Long,
    val analysisDate: String,
    val predictedClassLabel: String,
    val A1: Float,
    val A2: Float,
    val A3: Float,
    val A4: Float,
    val A5: Float,
    val A6: Float,
    val A7: Float
)

data class PredictionDetail(
    val modelName: String?,
    val diseaseName: String?,
    val A1: Double,
    val A2: Double,
    val A3: Double,
    val A4: Double,
    val A5: Double,
    val A6: Double,
    val A7: Double,
    val predictionDate: String,
)

