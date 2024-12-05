package com.rtl.petkinfe.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColorScheme(
    primary = PhotoIconActiveColor, // 메인 버튼과 맞춤 설정된 컬러
    secondary = Color.White, // 부드러운 느낌의 보조 색상
    background = Color.White, // 전체 배경 컬러
    surface = Color.White, // 카드 및 표면용 컬러
    onPrimary = Color.White, // primary 텍스트 색상
    onSecondary = Color.Black, // secondary 텍스트 색상
    onBackground = DefaultStrokeColor, // 배경 텍스트 색상
    onSurface = DefaultStrokeColor // 표면 텍스트 색상
)


@Composable
fun PetkinFETheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorPalette
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val systemUiController = rememberSystemUiController()

    // 상태바를 흰색으로 설정하고 아이콘을 어둡게
    systemUiController.setStatusBarColor(
        color = Color.White,
        darkIcons = true // 흰색 배경에 어울리는 어두운 아이콘
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}