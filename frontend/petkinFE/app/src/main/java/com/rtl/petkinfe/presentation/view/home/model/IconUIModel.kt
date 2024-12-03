package com.rtl.petkinfe.presentation.view.home.model

import androidx.compose.ui.graphics.Color

data class IconUIModel(
    val iconResId: Int,
    val contentDescription: String,
    val caption: String,
    val color: Color,
    val isActive: Boolean
)
