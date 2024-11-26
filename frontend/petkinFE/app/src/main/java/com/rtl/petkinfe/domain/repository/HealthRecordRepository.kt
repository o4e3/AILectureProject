package com.rtl.petkinfe.domain.repository

import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.model.ItemType

interface HealthRecordRepository {
    // 오늘의 전체 건강 기록 반환
    fun getTodayHealthRecord(): List<HealthRecord>

    // 특정 기록 ID로 상세 정보 반환
    fun getRecordInfo(recordId: Long): HealthRecord

    // 각 기록 유형의 존재 여부를 반환
    fun getItemExistence(): Map<ItemType, Long?>

    // 새로운 기록 추가
    fun addRecord(record: HealthRecord): HealthRecord

    // 기록 수정
    fun updateRecord(record: HealthRecord): HealthRecord

    // 기록 삭제
    fun deleteRecord(record: HealthRecord)

    // 이번 달의 각 기록 유형의 존재 여부 반환
    fun getMonthlyItemExistence(): Map<ItemType, Long?>
}