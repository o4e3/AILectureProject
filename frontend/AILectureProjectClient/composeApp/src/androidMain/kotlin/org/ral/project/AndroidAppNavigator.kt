package org.ral.project

import androidx.navigation.NavController

class AndroidAppNavigator(private val navController: NavController) : AppNavigator {
    override fun navigateToHome() {
        navController.navigate("home")
    }

    override fun navigateToLogin() {
        navController.navigate("login")
    }
}