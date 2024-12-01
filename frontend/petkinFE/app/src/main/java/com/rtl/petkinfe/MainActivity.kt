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
                if (isReady) {
                    PetkinNavigation()
                }
            }
        }
    }
}

