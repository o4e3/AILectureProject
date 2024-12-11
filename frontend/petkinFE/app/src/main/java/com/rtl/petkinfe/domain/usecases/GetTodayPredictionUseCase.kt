package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.Prediction
import com.rtl.petkinfe.domain.model.PredictionWithImage
import com.rtl.petkinfe.domain.repository.PetRepository
import com.rtl.petkinfe.domain.repository.PredictionRepository
import java.time.LocalDate
import javax.inject.Inject

class GetTodayPredictionUseCase @Inject constructor(
    private val petRepository: PetRepository,
    private val predictionRepository: PredictionRepository
) {
    suspend fun execute(): PredictionWithImage? {
        val petId = petRepository.getSavedPetId() ?: return null
        return predictionRepository.getPredictionsByDate(petId, LocalDate.now())
    }
}