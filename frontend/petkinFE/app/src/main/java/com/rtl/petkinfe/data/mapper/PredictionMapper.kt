package com.rtl.petkinfe.data.mapper

import com.rtl.petkinfe.data.remote.dto.PredictionResponseDto
import com.rtl.petkinfe.domain.model.Prediction

fun PredictionResponseDto.toDomain(): Prediction {
    return Prediction(
        analysisId = analysisId,
        analysisDate = analysisDate,
        predictedClassLabel = predictedClassLabel,
        A1 = A1,
        A2 = A2,
        A3 = A3,
        A4 = A4,
        A5 = A5,
        A6 = A6,
        A7 = A7
    )
}