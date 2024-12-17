package com.rtl.petkinfe.data.mapper

import com.rtl.petkinfe.data.remote.dto.CreateHealthRecordRequest
import com.rtl.petkinfe.data.remote.dto.CreateHealthRecordResponse
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

fun CreateHealthRecordResponse.toDomain(existingRecord: HealthRecord): HealthRecord {
    return existingRecord.copy(recordId = this.recordId)
}
