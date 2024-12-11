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

data class PredictionWithImage(
    val prediction: Prediction,
    val imageUrl: String
)

