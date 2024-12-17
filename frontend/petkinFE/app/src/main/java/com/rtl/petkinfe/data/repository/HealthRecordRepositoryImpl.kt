package com.rtl.petkinfe.data.repository

import com.rtl.petkinfe.data.mapper.toCreateHealthRecordRequest
import com.rtl.petkinfe.data.mapper.toDomain
import com.rtl.petkinfe.data.remote.api.HealthRecordApi
import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.repository.HealthRecordRepository
import java.time.LocalDate
import javax.inject.Inject

class HealthRecordRepositoryImpl @Inject constructor(
    private val healthRecordApi: HealthRecordApi
): HealthRecordRepository {
    override suspend fun getRecordByDate(petId: Long, date: LocalDate): List<HealthRecord> {
        val dateString = date.toString() // "YYYY-MM-DD" 형식
        return try {
            // 1. 날짜와 petId를 기준으로 데이터 조회
            val records = healthRecordApi.getHealthRecordsByDate(petId, dateString)
            // 2. DTO -> Domain 변환
            records.map { it.toDomain() }
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 404) {
                // 404 에러 시 빈 리스트 반환
                emptyList()
            } else {
                // 다른 예외는 다시 throw
                throw e
            }
        } catch (e: Exception) {
            // 그 외의 예외 처리
            throw e
        }
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