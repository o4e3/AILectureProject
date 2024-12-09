package com.rtl.petkinfe.presentation.view.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.model.ItemType
import com.rtl.petkinfe.domain.usecases.GetTodayRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodayRecordUseCase: GetTodayRecordUseCase
) : ViewModel() {

    private val _todayRecords = mutableStateOf(emptyList<HealthRecord>())
    val todayRecords: State<List<HealthRecord>> = _todayRecords

    private val _cardStates = mutableStateOf(mutableMapOf<ItemType, CardState>())
    val cardStates: State<Map<ItemType, CardState>> = _cardStates

    private val _iconStates = mutableStateOf(emptyMap<ItemType, Boolean>())
    val iconStates: State<Map<ItemType, Boolean>> = _iconStates

    init {
        loadRecords()
    }

    private fun loadRecords() {
        val records = getTodayRecordUseCase.execute()
        _todayRecords.value = records
        initializeCardStates(records)
        initializeIconStates(records)
    }

    private fun initializeCardStates(records: List<HealthRecord>) {
        val newStates = records.associate { record ->
            record.itemType to CardState(isExpanded = false, isPhotoUploaded = false)
        }
        _cardStates.value = newStates.toMutableMap()
    }

    private fun initializeIconStates(records: List<HealthRecord>) {
        val activeIcons = records.map { it.itemType }.toSet()
        val allIcons = ItemType.values().associateWith { it in activeIcons }
        _iconStates.value = allIcons
    }

    fun toggleCard(itemType: ItemType) {
        _cardStates.value = _cardStates.value.toMutableMap().apply {
            this[itemType]?.let {
                this[itemType] = it.copy(isExpanded = !it.isExpanded)
            }
        }
    }

    fun uploadPhoto(itemType: ItemType) {
        _cardStates.value = _cardStates.value.toMutableMap().apply {
            this[itemType]?.let {
                this[itemType] = it.copy(isPhotoUploaded = true)
            }
        }
    }
}


data class CardState(
    val isExpanded: Boolean = false,
    val isPhotoUploaded: Boolean = false
)
