package com.rtl.petkinfe.presentation.view.core.widgets


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rtl.petkinfe.R


@Composable
fun ExpandableCardSection() {
    // 카테고리 리스트 정의
    val categories = listOf(
        "피부 질환 검사" to Color(0xFFFFF1C1),
        "목욕" to Color(0xFFE1F5FE),
        "산책" to Color(0xFFDCEDC8),
        "간식" to Color(0xFFFCE4EC),
        "약" to Color(0xFFFFFBCB),
        "접종" to Color(0xFFA5A5A5),
        "병원" to Color(0xFFF5AFAF),
        "메모" to Color(0xFFCA9ECA),
    )

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .background(color = Color.White) // 배경색 설정
    ) {
        items(categories) { (title, color) ->
            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                // 타이틀 추가
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp),
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 12.dp),
                )
                ExpandableCard(color = color)
            }
        }
    }
}

@Composable
fun ExpandableCard(color: Color) {
    // 둥근 모서리를 위해 large shape 사용
    Card(
        Modifier
            .fillMaxWidth()
            .background(color = color, shape = RoundedCornerShape(44.dp))
            .clickable { /* 클릭 시 동작 처리 */ }
    ) {
        // Row 내부 요소를 세로 중심에 정렬
        Row(
            modifier = Modifier
                .background(color = color)
                .padding(12.dp, 4.dp)
                .fillMaxWidth()
                .toggleable(value = true, onValueChange = {
                    Modifier.height(200.dp)
                }),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically // 요소 세로 중앙 정렬
        ) {
            // '기록이 없습니다' 텍스트
            Text(
                text = "기록이 없습니다",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black // 가시성을 위해 필요시 조정
            )
            // '기록 추가' 버튼
            TextButton(
                onClick = { /* 기록 추가 동작 처리 */ }
            ) {
                Text("기록 추가", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}
