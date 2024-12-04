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
import com.rtl.petkinfe.presentation.view.pet.OnboardingScreen
import com.rtl.petkinfe.presentation.view.pet.PetRegistrationScreen
import com.rtl.petkinfe.presentation.view.user.UserScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetkinNavigation(startDestination: String) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // 로그인 화면과 펫 등록 화면에서만 BottomNavigation을 숨기기 위해 && 조건 사용
            if (currentRoute != PetkinScreens.LoginScreen.name && currentRoute != PetkinScreens.PetRegistrationScreen.name && currentRoute != PetkinScreens.OnboardingScreen.name) {
                BottomNavigation(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
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
            // 온 보딩 화면
            composable(PetkinScreens.OnboardingScreen.name) {
                OnboardingScreen(navController)
            }
            // 홈 화면
            composable(PetkinScreens.HomeScreen.name) {
                HomeScreen(navController)
            }
            // 펫 등록 화면
            composable(PetkinScreens.PetRegistrationScreen.name) {
                PetRegistrationScreen(navController)  // 펫이 없으면 펫 등록 화면으로 이동
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
