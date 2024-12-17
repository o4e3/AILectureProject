package com.rtl.petkinfe.presentation.view.home

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rtl.petkinfe.domain.model.ItemType
import com.rtl.petkinfe.domain.model.ItemTypeColors
import com.rtl.petkinfe.domain.model.ItemTypeTitles
import com.rtl.petkinfe.presentation.view.core.ExpandableCard
import com.rtl.petkinfe.presentation.view.core.IconSection
import com.rtl.petkinfe.utils.convertUriToFile
import com.rtl.petkinfe.utils.formatinHome
import java.time.LocalDateTime

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState
    val context = LocalContext.current // Context 가져오기

    // Toast 메시지 표시
    LaunchedEffect(uiState.message) {
        uiState.message?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.clearMessage() // 메시지 상태 초기화
        }
    }

    // 사진 선택 런처
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val file = convertUriToFile(context, uri)
            viewModel.uploadPhoto(ItemType.PHOTO, file)
        }
    }

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
                uiState = uiState,
                onToggle = viewModel::toggleCard,
                onPhotoUpload = { photoPickerLauncher.launch("image/*") }
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
    uiState: HomeUIState,
    onToggle: (ItemType) -> Unit,
    onPhotoUpload: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 18.dp)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        items(ItemType.values().toList()) { itemType ->
            // item_id와 itemType.id를 비교하여 record 찾기
            val record = uiState.records.find { it.itemType == ItemType.fromId(itemType.id) }
            val state = uiState.cardStates[itemType] ?: CardState()
            val backgroundColor = ItemTypeColors.backgroundColors[itemType] ?: Color.LightGray
            val title = ItemTypeTitles.titles[itemType] ?: "기타"

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                title,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                color = Color.Black,
                modifier = Modifier.padding(start = 6.dp, bottom = 2.dp)
            )
            ExpandableCard(
                title = title,
                color = backgroundColor,
                state = state,
                memo = record?.memo, // 기록이 있는 경우 memo 전달
                photoUrl = state.photoUrl,
                onToggle = { onToggle(itemType) },
                onPhotoUpload = if (itemType == ItemType.PHOTO) onPhotoUpload else ({})
            )
        }
    }
}


