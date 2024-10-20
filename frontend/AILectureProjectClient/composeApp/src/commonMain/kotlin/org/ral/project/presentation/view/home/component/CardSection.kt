package org.ral.project.presentation.view.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableCardSection() {
    val categories = listOf(
        "피부 질환 검사" to Color(0xFFFFF1C1),
        "목욕" to Color(0xFFE1F5FE),
        "산책" to Color(0xFFDCEDC8),
        "간식" to Color(0xFFFCE4EC),
        "약" to Color(0xFFFFF1C1)
    )

    Column {
        categories.forEach { (title, color) ->
            ExpandableCard(title = title, color = color)
        }
    }
}

@Composable
fun ExpandableCard(title: String, color: Color) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        backgroundColor = color,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold)
            if (expanded) {
                Text("기록이 없습니다", modifier = Modifier.padding(top = 8.dp))
                TextButton(onClick = { /* Handle Add Record */ }) {
                    Text("기록 추가", color = Color.Red)
                }
            } else {
                Text("기록이 없습니다", modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}
