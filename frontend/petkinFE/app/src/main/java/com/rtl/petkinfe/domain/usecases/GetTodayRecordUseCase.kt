package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.repository.HealthRecordRepository
import javax.inject.Inject

class GetTodayRecordUseCase @Inject constructor(
    private val healthRecordRepository: HealthRecordRepository
) {
    fun execute(): List<HealthRecord> {
        return healthRecordRepository.getTodayHealthRecord()
    }
}