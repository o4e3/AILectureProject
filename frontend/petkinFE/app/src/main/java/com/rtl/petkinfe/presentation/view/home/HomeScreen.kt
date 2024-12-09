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
import com.rtl.petkinfe.presentation.view.core.AddRecordButton
import com.rtl.petkinfe.presentation.view.core.CardContent
import com.rtl.petkinfe.presentation.view.core.CardHeader
import com.rtl.petkinfe.presentation.view.core.ExpandableCard
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

