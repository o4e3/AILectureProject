package com.rtl.petkinfe.presentation.view.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.LocalDateTime


@Composable
fun TitleSection() {
    // 오늘 날짜 가져오기
    val localDateTime: LocalDateTime = LocalDateTime.now()
    val currentDate: LocalDate = LocalDate.now()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "${currentDate.dayOfMonth}월 ${currentDate.dayOfMonth}일",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun IconSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.ic_camera), contentDescription = "사진", tint = Color.Gray)
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.ic_drop), contentDescription = "목욕", tint = Color.Gray)
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.ic_goal), contentDescription = "산책", tint = Color.Gray)
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.ic_bone), contentDescription = "간식", tint = Color.Gray)
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.ic_pen), contentDescription = "접종", tint = Color.Gray)
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.ic_heart), contentDescription = "병원", tint = Color.Gray)
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.ic_edit_box), contentDescription = "메모", tint = Color.Gray)
    }
}