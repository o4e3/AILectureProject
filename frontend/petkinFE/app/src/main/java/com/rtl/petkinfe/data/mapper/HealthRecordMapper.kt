package com.rtl.petkinfe.data.mapper

import android.util.Log
import com.rtl.petkinfe.data.remote.dto.CreateHealthRecordRequest
import com.rtl.petkinfe.data.remote.dto.CreateHealthRecordResponse
import com.rtl.petkinfe.data.remote.dto.HealthRecordResponse
import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.model.ItemType

fun HealthRecord.toCreateHealthRecordRequest(petId: Long): CreateHealthRecordRequest {
    return CreateHealthRecordRequest(
        petId = petId,
        itemId = this.itemType.toItemId(),
        memo = this.memo ?: "메모가 없습니다."
    )
}

fun ItemType.toItemId(): Int {
    return when (this) {
        ItemType.PHOTO -> 1
        ItemType.BATH -> 2
        ItemType.WALK -> 3
        ItemType.SNACK -> 4
        ItemType.MEDICINE -> 5
        ItemType.VACCINATION -> 6
        ItemType.HOSPITAL -> 7
        ItemType.MEMO -> 8
    }
}

fun Int.toItemType(): ItemType {
    return when (this) {
        1 -> ItemType.PHOTO
        2 -> ItemType.BATH
        3 -> ItemType.WALK
        4 -> ItemType.SNACK
        5 -> ItemType.MEDICINE
        6 -> ItemType.VACCINATION
        7 -> ItemType.HOSPITAL
        8 -> ItemType.MEMO
        else -> {
            Log.e("Mapper", "Unknown item type: $this") // 로그 추가
            ItemType.MEMO // 기본값 반환
        }
    }
}

fun CreateHealthRecordResponse.toDomain(existingRecord: HealthRecord): HealthRecord {
    return existingRecord.copy(recordId = this.recordId)
}

fun HealthRecordResponse.toDomain(): HealthRecord {
    Log.d("Mapper", "itemId: $itemId")
    val itemType = this.itemId.toItemType()
    Log.d("Mapper", "itemType: $itemType")
    return HealthRecord(
        recordId = this.recordId,
        itemType = itemType,
        memo = this.memo
    )
}