package com.rtl.petkinfe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rtl.petkinfe.ui.theme.PetkinFETheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Splash Screen 설정
        installSplashScreen().apply {
            setKeepVisibleCondition {
                !viewModel.isReady.value // ViewModel이 준비될 때까지 유지
            }
        }

        // Kakao SDK 초기화 완료 여부 확인
        setContent {
            PetkinFETheme {
                if (viewModel.isReady.collectAsState().value) {
                    AppNavigation() // 초기화 후 Navigation 실행
                }
            }
        }
    }
}

