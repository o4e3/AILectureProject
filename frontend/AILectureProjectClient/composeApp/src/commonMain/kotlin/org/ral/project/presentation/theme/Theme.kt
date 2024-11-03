package org.ral.project.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorPalette = lightColors(
    primary = PhotoIconActiveColor, // 메인 버튼과 맞춤 설정된 컬러
    primaryVariant = PhotoSectionBackgroundColor, // 밝은 느낌을 위한 변형 컬러
    secondary = BathIconActiveColor, // 부드러운 느낌의 보조 색상
    background = Color.White, // 전체 배경 컬러
    surface = MedicineSectionBackgroundColor, // 카드 및 표면용 컬러
    onPrimary = Color.White, // primary 텍스트 색상
    onSecondary = Color.Black, // secondary 텍스트 색상
    onBackground = DefaultStrokeColor, // 배경 텍스트 색상
    onSurface = DefaultStrokeColor // 표면 텍스트 색상
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
