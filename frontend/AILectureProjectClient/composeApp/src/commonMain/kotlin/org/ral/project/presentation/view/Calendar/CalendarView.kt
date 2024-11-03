package org.ral.project.presentation.view.Calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.ral.project.presentation.view.Calendar.component.CalendarContent
import org.ral.project.presentation.view.core.BottomNavigationBar
import org.ral.project.presentation.view.home.component.ExpandableCardSection
import org.ral.project.presentation.view.home.component.TitleSection

@Composable
fun CalendarView(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("캘린더") },
                navigationIcon = {
                    IconButton(onClick = { /* Do Something */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
            paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // 날짜 섹션과 캘린더 섹션을 추가
            TitleSection() // 날짜를 표시하는 섹션
            CalendarContent() // 캘린더 뷰 추가
            Divider(color= Color.Gray, thickness = 0.8.dp)
            Spacer(modifier = Modifier.height(20.dp))
            ExpandableCardSection()
        }
    }

}