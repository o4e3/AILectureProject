package org.ral.project.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.ral.project.presentation.view.core.BottomNavigationBar
import org.ral.project.presentation.view.home.component.ExpandableCardSection
import org.ral.project.presentation.view.home.component.IconSection
import org.ral.project.presentation.view.home.component.TitleSection

@Composable
fun HomeView(navController: NavHostController) {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    backgroundColor = Color.White,
                    title = { Text("홈") },
                    navigationIcon = {
                        IconButton(onClick = { /* Do Something */ }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
                Divider(
                    color = Color.Gray, // Set the color of the border
                    thickness = 0.5.dp   // Set the thickness of the border to be subtle
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            TitleSection()
            IconSection()
            ExpandableCardSection() // 확장 가능한 카드 섹션
        }
    }
}
