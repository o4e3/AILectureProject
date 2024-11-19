package com.rtl.petkinfe.presentation.view.user

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserView(navController: NavHostController) {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    modifier = Modifier.background(Color.White),
                    title = { Text("홈") },
                    navigationIcon = {
                        IconButton(onClick = { /* Do Something */ }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
                Divider(
                    color = Color.Gray, // Set the color of the border
                    thickness = 0.5.dp   // Set the thickness of the border to be subtle
                )
            }
        }
    ) {
        Text("UserView")
    }
}