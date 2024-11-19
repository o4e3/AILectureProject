package com.rtl.petkinfe

import androidx.compose.runtime.*
import com.rtl.petkinfe.presentation.view.login.LoginView
import com.rtl.petkinfe.ui.theme.PetkinFETheme

@Composable
fun App(navigator: AppNavigator) {
    PetkinFETheme {
        LoginView(navigator)
    }
}
