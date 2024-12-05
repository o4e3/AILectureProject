package com.rtl.petkinfe.presentation.view.home


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.usecases.GetTodayRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodayRecordUseCase: GetTodayRecordUseCase
) : ViewModel() {

    // 기록 데이터를 상태로 관리
    val todayRecords = mutableStateOf<List<HealthRecord>>(emptyList())

    // 초기화 시 데이터 가져오기
    init {
        fetchTodayRecords()
    }

    // 데이터를 가져오는 함수
    private fun fetchTodayRecords() {
        viewModelScope.launch {
            val records = getTodayRecordUseCase.execute()
            todayRecords.value = records
        }
    }
}
