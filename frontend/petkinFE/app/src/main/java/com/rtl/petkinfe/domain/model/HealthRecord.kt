package com.rtl.petkinfe.domain.model

import androidx.compose.ui.graphics.Color

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
    HOSPITAL,      // 병원
    MEMO
}

object ItemTypeColors {
    val backgroundColors = mapOf(
        ItemType.PHOTO to Color(0xFFFFF1C1),
        ItemType.BATH to Color(0xFFE1F5FE),
        ItemType.WALK to Color(0xffF1FFDC),
        ItemType.SNACK to Color(0xFFFCE4EC),
        ItemType.MEDICINE to Color(0xFFFFFBCB),
        ItemType.VACCINATION to Color(0xFF868EBD),
        ItemType.HOSPITAL to Color(0xFFF5AFAF),
        ItemType.MEMO to Color(0xFFCA9ECA)
    )
    val activeIconColors = mapOf(
        ItemType.PHOTO to Color(0xffFF9626),
        ItemType.BATH to Color(0xff009DFF),
        ItemType.WALK to Color(0xff75EE05),
        ItemType.SNACK to Color(0xffFCBDEB),
        ItemType.MEDICINE to Color(0xffFFC711),
        ItemType.VACCINATION to Color(0xFF5E6BB4),
        ItemType.HOSPITAL to Color(0xffF49393),
        ItemType.MEMO to Color(0xffC109C1)
    )
}


object ItemTypeTitles {
    val titles = mapOf(
        ItemType.PHOTO to "피부 질환 검사",
        ItemType.BATH to "목욕",
        ItemType.WALK to "산책",
        ItemType.SNACK to "간식",
        ItemType.MEDICINE to "약",
        ItemType.VACCINATION to "접종",
        ItemType.HOSPITAL to "병원",
        ItemType.MEMO to "메모"
    )
}
