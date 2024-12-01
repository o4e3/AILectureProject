package com.rtl.petkinfe.navigation

import BottomNavigation
import android.window.SplashScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rtl.petkinfe.presentation.view.calendar.CalendarScreen
import com.rtl.petkinfe.presentation.view.home.HomeScreen
import com.rtl.petkinfe.presentation.view.login.LoginScreen
import com.rtl.petkinfe.presentation.view.user.UserScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetkinNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != PetkinScreens.LoginScreen.name) {
                BottomNavigation(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = PetkinScreens.LoginScreen.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            // 로그인 화면
            composable(PetkinScreens.LoginScreen.name) {
                LoginScreen(onLoginSuccess = {
                    navController.navigate(PetkinScreens.HomeScreen.name) {
                        popUpTo(PetkinScreens.LoginScreen.name) { inclusive = true } // LoginScreen 제거
                        launchSingleTop = true
                    }
                })
            }
            // 홈 화면
            composable(PetkinScreens.HomeScreen.name) {
                HomeScreen(navController)
            }
            // 캘린더 화면
            composable(PetkinScreens.CalendarScreen.name) {
                CalendarScreen(navController)
            }
            // 마이페이지 화면
            composable(PetkinScreens.MyPageScreen.name) {
                UserScreen(navController)
            }
        }
    }
}
