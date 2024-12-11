package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.Prediction
import com.rtl.petkinfe.domain.repository.PetRepository
import com.rtl.petkinfe.domain.repository.PredictionRepository
import java.io.File
import javax.inject.Inject

class RequestPredictionUseCase @Inject constructor(
    private val predictionRepository: PredictionRepository,
    private val petRepository: PetRepository
) {
    suspend fun execute(imageFile: File): Prediction {
        val petId = petRepository.getSavedPetId()
        return predictionRepository.requestPrediction(petId!!, imageFile)
    }
}