package com.rtl.petkinfe.presentation.view.home


import BottomNavigation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.rtl.petkinfe.presentation.view.home.components.ExpandableCardSection
import com.rtl.petkinfe.presentation.view.home.components.IconSection
import com.rtl.petkinfe.presentation.view.home.components.TitleSection


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView() {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                    modifier = Modifier.background(Color.White),
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
        bottomBar = { BottomNavigation(navController = rememberNavController()) }
    ) {
        Column(modifier = Modifier.padding(it)) {
            TitleSection()
            IconSection()
            ExpandableCardSection() // 확장 가능한 카드 섹션
        }
    }
}