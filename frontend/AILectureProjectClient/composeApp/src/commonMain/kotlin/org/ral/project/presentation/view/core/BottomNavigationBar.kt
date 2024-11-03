package org.ral.project.presentation.view.core

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Gray,
        elevation = 8.dp
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "캘린더",
                    tint = if (currentRoute == "calendar") Color(0xFFFF9626) else Color.Gray // Active color and inactive color.
                )
            },
            label = {
                Text(
                    "캘린더",
                    color = if (currentRoute == "calendar") Color(0xFFFF9626) else Color.Gray, // Active color and inactive color.
                    fontSize = 12.sp // Adjust the font size to match the design.
                )
            },
            selected = currentRoute == "calendar",
            onClick = {
                navController.navigate("calendar") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "홈",
                    tint = if (currentRoute == "home") Color(0xFFFF9626) else Color.Gray
                )
            },
            label = {
                Text(
                    "홈",
                    color = if (currentRoute == "home") Color(0xFFFF9626) else Color.Gray,
                    fontSize = 12.sp
                )
            },
            selected = currentRoute == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "내 정보",
                    tint = if (currentRoute == "profile") Color(0xFFFF9626) else Color.Gray
                )
            },
            label = {
                Text(
                    "내 정보",
                    color = if (currentRoute == "profile") Color(0xFFFF9626) else Color.Gray,
                    fontSize = 12.sp
                )
            },
            selected = currentRoute == "profile",
            onClick = {
                navController.navigate("profile") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
