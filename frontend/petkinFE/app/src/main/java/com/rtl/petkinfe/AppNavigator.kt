package com.rtl.petkinfe

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rtl.petkinfe.presentation.view.home.HomeView
import com.rtl.petkinfe.presentation.view.login.LoginScreen

interface AppNavigator {
    fun navigateToLogin()
    fun navigateToHome()
}

@Composable
fun AppNavigation() {
    // NavController 생성
    val navController = rememberNavController()

    // NavHost로 화면 구성
    NavHost(
        navController = navController,
        startDestination = "login" // 시작 화면을 LoginView로 설정
    ) {
        composable("login") {
            LoginScreen() // LoginView Composable 호출
        }

        // 홈 화면 경로 추가
        composable("home") {
            HomeView() // HomeView Composable 호출
        }
    }
}
