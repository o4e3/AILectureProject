package com.rtl.petkinfe.data.repository

import com.rtl.petkinfe.data.mapper.toCreateHealthRecordRequest
import com.rtl.petkinfe.data.mapper.toDomain
import com.rtl.petkinfe.data.remote.api.HealthRecordApi
import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.repository.HealthRecordRepository
import javax.inject.Inject

class HealthRecordRepositoryImpl @Inject constructor(
    private val healthRecordApi: HealthRecordApi
): HealthRecordRepository {
    override suspend fun getTodayHealthRecord(): List<HealthRecord> {
        TODO("Not yet implemented")
    }

    override fun getRecordInfo(recordId: Long): HealthRecord {
        TODO("Not yet implemented")
    }

    override suspend fun addRecord(petId: Long, record: HealthRecord): HealthRecord {
        val response = healthRecordApi.createHealthRecord(record.toCreateHealthRecordRequest(petId))
        return response.toDomain(record)
    }

    override fun updateRecord(record: HealthRecord): HealthRecord {
        TODO("Not yet implemented")
    }

    override fun deleteRecord(record: Long) {
        TODO("Not yet implemented")
    }

    override fun getMonthlyItemExistence(month: String): List<HealthRecord> {
        TODO("Not yet implemented")
    }

    override fun getHealthRecordsByDate(date: String): List<HealthRecord> {
        TODO("Not yet implemented")
    }

}