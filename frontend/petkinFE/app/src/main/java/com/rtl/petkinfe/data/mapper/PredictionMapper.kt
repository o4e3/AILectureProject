package com.rtl.petkinfe.data.mapper

import com.rtl.petkinfe.data.remote.dto.PredictionDetailResponseDto
import com.rtl.petkinfe.data.remote.dto.RequestPredictionResponseDto
import com.rtl.petkinfe.domain.model.Prediction


fun RequestPredictionResponseDto.toDomain(): Prediction {
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

fun PredictionDetailResponseDto.toDomain(timestamp: String): Prediction {
    // A1~A7 값을 리스트로 묶기
    val probabilities = listOf(A1, A2, A3, A4, A5, A6, A7)
    val labels = listOf(
        "구진/플라크",
        "비듬/각질/상피성잔고리",
        "태선화/과다색소침착",
        "농포/여드름",
        "미란/궤양",
        "결정/종괴",
        "무증상"
    )

    // 최대 확률값의 인덱스 가져오기
    val maxIndex = probabilities.indexOf(probabilities.maxOrNull())

    // 해당 인덱스에 대응하는 라벨 가져오기
    val predictedLabel = if (maxIndex in labels.indices) labels[maxIndex] else "알 수 없음"

    return Prediction(
        analysisId = analysisId,
        analysisDate = timestamp,
        predictedClassLabel = predictedLabel, // 최대 확률값에 해당하는 라벨
        A1 = A1,
        A2 = A2,
        A3 = A3,
        A4 = A4,
        A5 = A5,
        A6 = A6,
        A7 = A7
    )
}