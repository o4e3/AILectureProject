package org.ral.project

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ral.project.presentation.theme.CustomAppTheme
import org.ral.project.presentation.view.login.LoginView

@Composable
@Preview
fun App(navigator: AppNavigator) {
    CustomAppTheme {
        LoginView(navigator)
    }
}