package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.repository.HealthRecordRepository
import javax.inject.Inject

class SaveHealthRecordsUseCase @Inject constructor(
    private val healthRecordRepository: HealthRecordRepository
) {
    suspend operator fun invoke(healthRecord: HealthRecord): HealthRecord {
        return healthRecordRepository.addRecord(healthRecord)
    }
}