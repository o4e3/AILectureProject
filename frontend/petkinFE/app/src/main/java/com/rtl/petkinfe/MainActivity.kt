package com.rtl.petkinfe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rtl.petkinfe.presentation.view.login.LoginView
import com.rtl.petkinfe.ui.theme.PetkinFETheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            // ToDO : check login status
            setKeepVisibleCondition {
                !viewModel.isReady.value
            }
        }
        setContent {
            PetkinFETheme {
                AppNavigation()
            }
        }
    }
}

