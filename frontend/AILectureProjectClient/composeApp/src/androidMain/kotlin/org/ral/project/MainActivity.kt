package org.ral.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.ral.project.presentation.view.HomeView
import org.ral.project.presentation.view.login.LoginView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val navigator = AndroidAppNavigator(navController)

            NavHost(navController = navController, startDestination = "login") {
                composable("login") { LoginView(navigator) }
                composable("home") { HomeView() }
            }
        }
    }
}
