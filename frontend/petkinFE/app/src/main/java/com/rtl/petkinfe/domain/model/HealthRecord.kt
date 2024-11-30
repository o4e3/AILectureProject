package com.rtl.petkinfe.domain.model
data class HealthRecord(
    val recordId: Long,
    val itemType: ItemType,
    val memo: String? = null
)


// 기록 유형 Enum 정의
enum class ItemType {
    PHOTO,        // 사진
    BATH,         // 목욕
    WALK,         // 산책
    SNACK,        // 간식
    MEDICINE,     // 약
    VACCINATION,  // 접종
    HOSPITAL      // 병원
}