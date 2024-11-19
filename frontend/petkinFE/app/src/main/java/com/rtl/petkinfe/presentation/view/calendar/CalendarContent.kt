package com.rtl.petkinfe.presentation.view.calendar


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.LocalDateTime



@Composable
fun CalendarContent() {

    // 오늘 날짜 가져오기
    val localDateTime: LocalDateTime = LocalDateTime.now()
    val localDate: LocalDate = LocalDate.now()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White) // 전체 배경을 흰색으로 설정
            .padding(vertical = 16.dp) // 세로 패딩 추가
    ) {
        // 요일 헤더 (일, 월, 화, ...)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("일", "월", "화", "수", "목", "금", "토").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center // 수정된 부분
                )
            }
        }

        // 임시로 5주 표시 (7 * 5 = 35일)
        for (week in 0 until 5) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (day in 0 until 7) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(Color.White)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // 간단한 날짜 표시 (임시 데이터)
                        Text(text = "${week * 7 + day + 1}", fontSize = 14.sp)
                    }
                }
            }
        }
    }
}