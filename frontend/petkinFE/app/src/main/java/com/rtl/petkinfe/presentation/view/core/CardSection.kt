package com.rtl.petkinfe.presentation.view.core

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.rtl.petkinfe.BuildConfig.API_BASE_URL

import com.rtl.petkinfe.R
import com.rtl.petkinfe.domain.model.ItemType
import com.rtl.petkinfe.presentation.view.home.CardState
import com.rtl.petkinfe.presentation.view.home.HomeViewModel
import com.rtl.petkinfe.ui.theme.PhotoIconActiveColor
import com.rtl.petkinfe.ui.theme.SplashBackgroundColor

@Composable
fun CardContent(
    title: String,
    memo: String?,
    cardState: CardState,
    onPhotoUpload: (() -> Unit),
) {
    val photoUrl = cardState.photoUrl

    Column(modifier = Modifier.padding(16.dp)) {
        if (title == "피부 질환 검사") {
            PhotoContent(photoUrl, onPhotoUpload, cardState = cardState)
        } else {
            GeneralCardContent(memo)
        }
    }
}

@Composable
fun PhotoContent(
    photoUrl: String?,
    onPhotoUpload: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    cardState: CardState
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!photoUrl.isNullOrEmpty()) {
            Image(
                painter = rememberImagePainter(
                    data = photoUrl,
                    builder = {
                        placeholder(R.drawable.res_dog)
                        error(R.drawable.res_dog)
                    }
                ),
                contentDescription = "Uploaded Photo",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            UploadCameraButton(onClick = onPhotoUpload, buttonText = "사진 변경")
            Spacer(modifier = Modifier.height(24.dp))
            RequestPredictionButton(onClick = { viewModel.requestPrediction() }, onActive = true)
        } else {
            Text(
                "사진이 없습니다",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            UploadCameraButton(onClick = onPhotoUpload, buttonText = "사진 등록")
            Spacer(modifier = Modifier.height(24.dp))
            // 알림창으로 '사진을 등록해주세요' 메시지 표시
            RequestPredictionButton(onClick = { /* TODO */ }, onActive = false)
        }

        // 예측 결과 표시
        cardState.prediction?.let { prediction ->
            Spacer(modifier = Modifier.height(16.dp))
            val diseaseMapping = listOf(
                "구진/플라크",
                "비듬/각질/상피성잔고리",
                "태선화 과다색소침착",
                "농포/여드름",
                "미란/궤양",
                "결정/종괴",
                "무증상"
            )

            diseaseMapping.forEachIndexed { index, disease ->
                val probability = when (index) {
                    0 -> prediction.A1
                    1 -> prediction.A2
                    2 -> prediction.A3
                    3 -> prediction.A4
                    4 -> prediction.A5
                    5 -> prediction.A6
                    6 -> prediction.A7
                    else -> 0f
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = disease, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = String.format("%.2f", probability),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "예측 결과: ${prediction.predictedClassLabel}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

        }

    }
}

@Composable
fun RequestPredictionButton(
    onClick: () -> Unit,
    onActive: Boolean
) {
    Box(
        modifier = Modifier
            .size(width = 240.dp, height = 36.dp)
            .background(
                color = if (onActive) Color(0xFFFFA500) else Color.LightGray, // 활성 상태에 따라 색상 변경
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(enabled = onActive, onClick = onClick) // 활성 상태에 따라 클릭 가능 여부 설정
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (onActive) "피부 질환 검사하기" else "사진을 등록해주세요",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun GeneralCardContent(memo: String?) {
    Text(
        text = "📋 메모",
        style = MaterialTheme.typography.titleMedium,
        color = Color.Black
    )
    Spacer(modifier = Modifier.height(8.dp))
    if (memo != null) {
        Text(
            text = memo,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}

@Composable
fun UploadCameraButton(
    onClick: () -> Unit,
    buttonText: String
) {
    Log.d("testt", "사진 등록 클릭")
    Box(
        modifier = Modifier
            .border(
                BorderStroke(1.dp, PhotoIconActiveColor),
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = SplashBackgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp) // 최소한의 패딩으로 높이를 줄임
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = buttonText,
                tint = PhotoIconActiveColor,
                modifier = Modifier.size(12.dp) // 아이콘 크기 줄임
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = buttonText,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                color = PhotoIconActiveColor
            )
        }
    }
}


@Composable
fun CardHeader(
    title: String,
    isExpanded: Boolean,
    memo: String?,
    onToggle: () -> Unit,
    onAddRecord: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (memo == null) "기록이 없습니다" else "기록이 있습니다",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(start = 6.dp)
        )
        HeaderActionButtons(title, isExpanded, memo, onToggle, onAddRecord)
    }
}

@Composable
fun HeaderActionButtons(
    title: String,
    isExpanded: Boolean,
    memo: String?,
    onToggle: () -> Unit,
    onAddRecord: () -> Unit
) {
    when {
        title == "피부 질환 검사" -> {
            ToggleButton(isExpanded, onToggle)
        }

        memo == null -> {
            AddRecordButton(onClick = onAddRecord)
        }

        else -> {
            ToggleButton(isExpanded, onToggle)
        }
    }
}

@Composable
fun ToggleButton(isExpanded: Boolean, onToggle: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(end = 6.dp)
            .clickable { onToggle() }
    ) {
        Text(
            text = if (isExpanded) "닫기" else "열기",
            color = Color.Black,
            fontSize = 14.sp
        )
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@Composable
fun AddRecordButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(32.dp)
            .background(
                color = SplashBackgroundColor,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                BorderStroke(0.4.dp, PhotoIconActiveColor),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "기록 추가",
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun ExpandableCard(
    title: String,
    color: Color,
    state: CardState,
    memo: String?,
    photoUrl: String?, // URL 추가
    onToggle: () -> Unit,
    onPhotoUpload: () -> Unit // 런처 호출 콜백
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onToggle() },
        shape = RoundedCornerShape(26.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color)
                .padding(12.dp)
        ) {
            CardHeader(
                title = title,
                isExpanded = state.isExpanded,
                memo = memo,
                onToggle = onToggle,
                onAddRecord = { TODO() })
            if (state.isExpanded) {
                CardContent(
                    title = title,
                    memo = memo,
                    onPhotoUpload = onPhotoUpload, // 기본값 제공
                    cardState = state
                )
            }
        }
    }
}

@Composable
fun RenderPredictionImage(imageUrl: String) {
    val baseUrl = API_BASE_URL.removeSuffix("/") // 마지막 "/" 제거
    val fullImageUrl = "$baseUrl$imageUrl"
    Log.d("testt", "fullImageUrl: $fullImageUrl")
    Image(
        painter = rememberImagePainter(
            data = fullImageUrl,
            builder = {
                placeholder(R.drawable.res_dog) // 로딩 중 표시할 이미지
                error(R.drawable.res_dog)           // 로드 실패 시 표시할 이미지
            }
        ),
        contentDescription = "Prediction Image",
        modifier = Modifier
            .size(200.dp) // 이미지 크기 조정
            .padding(8.dp),
        contentScale = ContentScale.Crop
    )
}
