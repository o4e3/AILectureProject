package com.rtl.petkinfe.domain.model

data class Prediction(
    val id: Long?,
    val analysisId: Long,
    val diseaseId: Int,
    val predictionDate: String
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