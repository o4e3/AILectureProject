package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.repository.HealthRecordRepository
import com.rtl.petkinfe.domain.repository.PetRepository
import javax.inject.Inject

class SaveHealthRecordsUseCase @Inject constructor(
    private val healthRecordRepository: HealthRecordRepository,
    private val petRepository: PetRepository
) {
    suspend operator fun invoke(healthRecord: HealthRecord): HealthRecord {
        val petId = petRepository.getSavedPetId()
        return healthRecordRepository.addRecord(petId!!, healthRecord)
    }
}