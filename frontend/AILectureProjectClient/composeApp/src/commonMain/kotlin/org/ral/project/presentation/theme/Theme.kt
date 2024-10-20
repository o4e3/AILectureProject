package org.ral.project.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryVariantColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    onPrimary = OnPrimaryColor,
    onSecondary = OnSecondaryColor
)

@Composable
fun CustomAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette, // 커스텀 팔레트 적용
        typography = MaterialTheme.typography, // 필요시 커스텀 타이포그래피 추가 가능
        shapes = MaterialTheme.shapes,
        content = content
    )
}
