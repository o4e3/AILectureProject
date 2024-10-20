package org.ral.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import ailectureprojectclient.composeapp.generated.resources.Res
import ailectureprojectclient.composeapp.generated.resources.compose_multiplatform
import androidx.compose.material.MaterialTheme.colors
import org.ral.project.presentation.theme.CustomAppTheme
import org.ral.project.presentation.view.HomeView

@Composable
@Preview
fun App() {
    CustomAppTheme {
        // 바로 HomeView 로 이동
        HomeView()
    }
}