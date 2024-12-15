package com.rtl.petkinfe.presentation.view.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rtl.petkinfe.R
import com.rtl.petkinfe.domain.model.ItemType
import com.rtl.petkinfe.domain.model.ItemTypeColors
import com.rtl.petkinfe.domain.model.ItemTypeTitles
import com.rtl.petkinfe.presentation.view.home.HomeViewModel
import com.rtl.petkinfe.presentation.view.home.model.IconUIModel


@Composable
fun IconSection(viewModel: HomeViewModel = hiltViewModel()) {
    val iconStates by viewModel.iconStates

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ItemType.values().forEach { itemType ->
            val iconColor = if (iconStates[itemType] == true) {
                ItemTypeColors.activeIconColors[itemType] ?: Color.Gray
            } else {
                Color.Gray
            }

            val iconResId = when (itemType) {
                ItemType.PHOTO -> R.drawable.ic_camera
                ItemType.BATH -> R.drawable.ic_drop
                ItemType.WALK -> R.drawable.ic_goal
                ItemType.SNACK -> R.drawable.ic_bone
                ItemType.MEDICINE -> R.drawable.ic_capsule
                ItemType.VACCINATION -> R.drawable.ic_pen
                ItemType.HOSPITAL -> R.drawable.ic_heart
                ItemType.MEMO -> R.drawable.ic_edit_box
            }

            val caption = if (itemType == ItemType.PHOTO) {
                "사진" // '피부질환검사' 대신 '사진' 사용
            } else {
                ItemTypeTitles.titles[itemType] ?: "기타"
            }

            IconWithCaption(
                IconUIModel(
                    iconResId = iconResId,
                    contentDescription = itemType.name,
                    caption = caption,
                    color = iconColor,
                    isActive = iconStates[itemType] == true
                )
            )
        }
    }
}


@Composable
fun IconWithCaption(item: IconUIModel) {
    val iconColor = if (item.isActive) item.color else Color.Gray
    val textColor = if (item.isActive) item.color else Color.Gray

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Icon(
            painter = painterResource(id = item.iconResId),
            contentDescription = item.contentDescription,
            tint = iconColor,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = item.caption,
            fontSize = 12.sp,
            color = textColor
        )
    }
}