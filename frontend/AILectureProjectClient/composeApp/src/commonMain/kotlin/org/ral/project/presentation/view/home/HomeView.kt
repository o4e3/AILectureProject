package org.ral.project.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.ral.project.presentation.view.home.component.ExpandableCardSection
import org.ral.project.presentation.view.home.component.TitleSection

@Composable
fun HomeView() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("홈") },
                navigationIcon = {
                    IconButton(onClick = { /* Do Something */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "캘린더") },
                    label = { Text("캘린더") },
                    selected = false,
                    onClick = { /* Handle navigation */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "홈") },
                    label = { Text("홈") },
                    selected = true,
                    onClick = { /* Handle navigation */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "내 정보") },
                    label = { Text("내 정보") },
                    selected = false,
                    onClick = { /* Handle navigation */ }
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            TitleSection() // 날짜와 아이콘 섹션
            ExpandableCardSection() // 확장 가능한 카드 섹션
        }
    }
}
