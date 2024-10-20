package org.ral.project.presentation.view.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TitleSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // TODO: 10월 20일을 현재 날짜로 변경
        Text("10월 20일", style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(Icons.Default.Phone, contentDescription = "사진", tint = Color.Gray)
            Icon(Icons.Default.Phone, contentDescription = "목욕", tint = Color.Gray)
            Icon(Icons.Default.Phone, contentDescription = "산책", tint = Color.Gray)
            Icon(Icons.Default.Phone, contentDescription = "간식", tint = Color.Gray)
            Icon(Icons.Default.Phone, contentDescription = "병원", tint = Color.Gray)
            Icon(Icons.Default.Phone, contentDescription = "메모", tint = Color.Gray)
        }
    }
}
