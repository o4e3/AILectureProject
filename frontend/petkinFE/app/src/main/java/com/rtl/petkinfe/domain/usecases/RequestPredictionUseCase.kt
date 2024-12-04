package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.Prediction
import com.rtl.petkinfe.domain.repository.PredictionRepository
import java.io.File
import javax.inject.Inject

class RequestPredictionUseCase @Inject constructor(
    private val predictionRepository: PredictionRepository
) {
    fun execute(petId: Long, imageFile: File): Prediction {
        return predictionRepository.requestPrediction(petId, imageFile)
    }
}