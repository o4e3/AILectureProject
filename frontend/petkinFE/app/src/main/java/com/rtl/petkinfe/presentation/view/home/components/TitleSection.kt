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
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.camera_icon), contentDescription = "사진", tint = Color.Gray)
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.drop_icon), contentDescription = "목욕", tint = Color.Gray)
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.goal_icon), contentDescription = "산책", tint = Color.Gray)
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.bone_icon), contentDescription = "간식", tint = Color.Gray)
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.pen_icon), contentDescription = "접종", tint = Color.Gray)
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.heart_icon), contentDescription = "병원", tint = Color.Gray)
        Icon(painter = painterResource(com.rtl.petkinfe.R.drawable.edit_box_icon), contentDescription = "메모", tint = Color.Gray)
    }
}