package com.rtl.petkinfe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.rtl.petkinfe.navigation.PetkinNavigation
import com.rtl.petkinfe.navigation.PetkinScreens
import com.rtl.petkinfe.ui.theme.PetkinFETheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        // Splash Screen 설정
        installSplashScreen().apply {
            setKeepVisibleCondition {
                !viewModel.isReady.value
            }

        }

        // Kakao SDK 초기화 완료 여부 확인
        setContent {
            PetkinFETheme {
                    val isReady by viewModel.isReady.collectAsState()
                    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
                    val petRegistered by viewModel.petRegistered.collectAsState()

                if (isReady) {
                    // 로그인 상태에 따라 화면 전환
                    if (isLoggedIn) {
                        if (petRegistered == true) {
                            // 펫이 등록되어 있으면 홈 화면으로
                            PetkinNavigation(startDestination = PetkinScreens.HomeScreen.name)
                        } else {
                            // 펫이 등록되지 않았다면 펫 등록 화면으로
                            PetkinNavigation(startDestination = PetkinScreens.OnboardingScreen.name)
                        }
                    } else {
                        // 로그인하지 않았다면 로그인 화면으로
                        PetkinNavigation(startDestination = PetkinScreens.LoginScreen.name)
                    }
                }

            }
        }
    }
}

