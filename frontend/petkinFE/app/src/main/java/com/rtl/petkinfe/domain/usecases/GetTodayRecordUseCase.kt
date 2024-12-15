package com.rtl.petkinfe.domain.usecases

import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.model.ItemType
import com.rtl.petkinfe.domain.repository.HealthRecordRepository
import javax.inject.Inject

class GetTodayRecordUseCase @Inject constructor(
    private val healthRecordRepository: HealthRecordRepository
) {
    fun execute(): List<HealthRecord> {
        return listOf(
            HealthRecord(
                recordId = 1,
                itemType = ItemType.PHOTO,
                memo = "https://example.com/photo.jpg" // URL 메모
            ),
            HealthRecord(
                recordId = 3,
                itemType = ItemType.WALK,
                memo = "30분 산책"
            ),
            HealthRecord(
                recordId = 4,
                itemType = ItemType.SNACK,
                memo = "간식으로 닭고기 제공"
            ),
            HealthRecord(
                recordId = 5,
                itemType = ItemType.MEDICINE,
                memo = "약 복용 완료"
            ),
            HealthRecord(
                recordId = 6,
                itemType = ItemType.VACCINATION,
                memo = "예방접종 완료"
            ),
            HealthRecord(
                recordId = 7,
                itemType = ItemType.HOSPITAL,
                memo = "정기 검진"
            ),
            HealthRecord(
                recordId = 8,
                itemType = ItemType.MEMO,
                memo = "다음 주 추가 병원 방문 예정"
            )
        )
        //return healthRecordRepository.getTodayHealthRecord()
    }
}