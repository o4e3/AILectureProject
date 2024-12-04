package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.repository.HealthRecordRepository
import javax.inject.Inject

class UpdateHealthRecordUseCase @Inject constructor(
    private val healthRecordRepository: HealthRecordRepository
) {
    fun execute(healthRecord: HealthRecord): HealthRecord {
        return healthRecordRepository.updateRecord(healthRecord)
    }
}