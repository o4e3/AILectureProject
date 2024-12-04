package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.repository.HealthRecordRepository
import javax.inject.Inject

class DeleteHealthRecordUseCase @Inject constructor(
    private val healthRecordRepository: HealthRecordRepository
) {
    fun execute(id: Long) {
        healthRecordRepository.deleteRecord(id)
    }
}