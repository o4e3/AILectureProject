package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.model.ItemType
import com.rtl.petkinfe.domain.model.PredictionWithImage
import com.rtl.petkinfe.domain.repository.HealthRecordRepository
import com.rtl.petkinfe.domain.repository.PetRepository
import com.rtl.petkinfe.domain.repository.PredictionRepository
import java.time.LocalDate
import javax.inject.Inject

class GetTodayRecordUseCase @Inject constructor(
    private val petRepository: PetRepository,
    private val healthRecordRepository: HealthRecordRepository
) {
    suspend fun execute(): List<HealthRecord>? {
        val petId = petRepository.getSavedPetId() ?: return null
        return healthRecordRepository.getRecordByDate(petId, LocalDate.now())
    }
}