package com.rtl.petkinfe.presentation.view.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.protobuf.Internal.BooleanList
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rtl.petkinfe.R
import com.rtl.petkinfe.domain.model.HealthRecord
import com.rtl.petkinfe.domain.model.ItemType
import com.rtl.petkinfe.domain.model.ItemTypeColors
import com.rtl.petkinfe.domain.model.ItemTypeTitles
import com.rtl.petkinfe.presentation.view.core.IconSection
import com.rtl.petkinfe.presentation.view.home.model.IconUIModel
import com.rtl.petkinfe.ui.theme.PhotoIconActiveColor
import com.rtl.petkinfe.ui.theme.SplashBackgroundColor
import com.rtl.petkinfe.utils.formatDate
import com.rtl.petkinfe.utils.formatinHome
//import com.rtl.petkinfe.presentation.view.core.widgets.ExpandableCardSection
import java.time.LocalDate
import java.time.LocalDateTime

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val todayRecords by viewModel.todayRecords
    val cardStates by viewModel.cardStates

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("홈") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Navigation */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(12.dp)
        ) {
            TitleSection()
            IconSection()
            Spacer(modifier = Modifier.height(16.dp))
            ExpandableCardSection(
                records = todayRecords,
                cardStates = cardStates,
                onToggle = { viewModel.toggleCard(it) },
                onPhotoUpload = { viewModel.uploadPhoto(it) }
            )
        }
    }
}

@Composable
fun TitleSection() {
    // 오늘 날짜 가져오기
    val localDateTime: LocalDateTime = LocalDateTime.now()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start = 6.dp)
    ) {
        Text(
            formatinHome(localDateTime.toString()),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}


@Composable
fun ExpandableCardSection(
    records: List<HealthRecord>,
    cardStates: Map<ItemType, CardState>,
    onToggle: (ItemType) -> Unit,
    onPhotoUpload: (ItemType) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        items(ItemType.values().toList()) { itemType ->
            val record = records.find { it.itemType == itemType }
            val state = cardStates[itemType] ?: CardState()
            val backgroundColor = ItemTypeColors.backgroundColors[itemType] ?: Color.LightGray
            val title = ItemTypeTitles.titles[itemType] ?: "기타"
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp), color = Color.Black, modifier = Modifier.padding(start = 6.dp, bottom = 2.dp))
            ExpandableCard(
                title = title,
                color = backgroundColor,
                state = state,
                memo = record?.memo,
                onToggle = { onToggle(itemType) },
                onPhotoUpload = { onPhotoUpload(itemType) }
            )
        }
    }
}


@Composable
fun ExpandableCard(
    title: String,
    color: Color,
    state: CardState,
    memo: String?,
    onToggle: () -> Unit,
    onPhotoUpload: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { onToggle() },
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color)
                .padding(12.dp)
        ) {
            CardHeader(title = title, isExpanded = state.isExpanded, memo =  memo, onToggle = onToggle, onAddRecord = { TODO()})
            if (state.isExpanded) {
                CardContent(
                    title = title,
                    isPhotoUploaded = state.isPhotoUploaded,
                    memo = memo,
                    onPhotoUpload = onPhotoUpload
                )
            }
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
            .height(36.dp), // 동일한 높이로 설정
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 기록 여부에 따라 표시 텍스트 변경
        Text(
            text = if (memo == null) "기록이 없습니다" else "기록이 있습니다",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(start = 6.dp)
        )
        when {
            title == "피부 질환 검사" -> {
                // 피부 질환 검사는 열기/닫기 버튼만 표시
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 6.dp)
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
            memo == null -> {
                // 기록이 없는 경우 '기록 추가' 버튼 표시
                AddRecordButton {
                    onAddRecord()
                }
            }
            else -> {
                // 기록이 있는 경우 '열기/닫기' 버튼 표시
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onToggle() }
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
        }
    }
}



@Composable
fun CardContent(
    title: String,
    isPhotoUploaded: Boolean,
    memo: String?,
    onPhotoUpload: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (title == "피부 질환 검사") {
            TextButton(onClick = { onPhotoUpload() }) {
                Text(
                    text = if (!isPhotoUploaded) "사진 업로드" else "사진 업로드 완료",
                    color = if (!isPhotoUploaded) Color.Blue else Color.Green
                )
            }
        } else {
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
    }
}





@Composable
fun AddRecordButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(32.dp)
            .background(
                color = SplashBackgroundColor, // 버튼 배경색
                shape = RoundedCornerShape(24.dp) // 둥근 모서리
            )
            .border(
                BorderStroke(0.4.dp, PhotoIconActiveColor), // 테두리
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(onClick = onClick) // 클릭 이벤트 추가
            .padding(horizontal = 12.dp), // 패딩 설정
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
