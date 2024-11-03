package org.ral.project.presentation.view.home.component

import ailectureprojectclient.composeapp.generated.resources.Res
import ailectureprojectclient.composeapp.generated.resources.bone_icon
import ailectureprojectclient.composeapp.generated.resources.camera_icon
import ailectureprojectclient.composeapp.generated.resources.drop_icon
import ailectureprojectclient.composeapp.generated.resources.edit_box_icon
import ailectureprojectclient.composeapp.generated.resources.goal_icon
import ailectureprojectclient.composeapp.generated.resources.heart_icon
import ailectureprojectclient.composeapp.generated.resources.pen_icon
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource


@Composable
fun TitleSection() {
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "${currentDate.monthNumber}월 ${currentDate.dayOfMonth}일",
            style = MaterialTheme.typography.h4,
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
        Icon(painter = painterResource(Res.drawable.camera_icon), contentDescription = "사진", tint = Color.Gray)
        Icon(painter = painterResource(Res.drawable.drop_icon), contentDescription = "목욕", tint = Color.Gray)
        Icon(painter = painterResource(Res.drawable.goal_icon), contentDescription = "산책", tint = Color.Gray)
        Icon(painter = painterResource(Res.drawable.bone_icon), contentDescription = "간식", tint = Color.Gray)
        Icon(painter = painterResource(Res.drawable.pen_icon), contentDescription = "접종", tint = Color.Gray)
        Icon(painter = painterResource(Res.drawable.heart_icon), contentDescription = "병원", tint = Color.Gray)
        Icon(painter = painterResource(Res.drawable.edit_box_icon), contentDescription = "메모", tint = Color.Gray)
    }
}
