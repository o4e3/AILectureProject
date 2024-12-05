package com.rtl.petkinfe.presentation.view.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.protobuf.Internal.BooleanList
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rtl.petkinfe.R
import com.rtl.petkinfe.presentation.view.home.model.IconUIModel
import com.rtl.petkinfe.utils.formatDate
import com.rtl.petkinfe.utils.formatinHome
//import com.rtl.petkinfe.presentation.view.core.widgets.ExpandableCardSection
import java.time.LocalDate
import java.time.LocalDateTime

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Navigation drawer or back action */ }) {
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
            ExpandableCardSection() // 확장 가능한 카드 섹션
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun HomeScreenPreview() {
    Scaffold(
    ) {
        HomeScreen(navController = rememberNavController())
    }
}


@Composable
fun TitleSection() {
    // 오늘 날짜 가져오기
    val localDateTime: LocalDateTime = LocalDateTime.now()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp, start = 6.dp)
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
fun IconSection() {
    val iconItems = listOf(
        IconUIModel(
            iconResId = R.drawable.ic_camera,
            contentDescription = "사진",
            caption = "사진",
            color = Color(0xffFF9626),
            isActive = true
        ),
        IconUIModel(
            iconResId = R.drawable.ic_drop,
            contentDescription = "목욕",
            caption = "목욕",
            color = Color(0xff009DFF),
            isActive = false
        ),
        IconUIModel(
            iconResId = R.drawable.ic_goal,
            contentDescription = "산책",
            caption = "산책",
            color = Color(0xff75EE05),
            isActive = true
        ),
        IconUIModel(
            iconResId = R.drawable.ic_bone,
            contentDescription = "간식",
            caption = "간식",
            color = Color(0xffFCBDEB),
            isActive = false
        ),
        IconUIModel(
            iconResId = R.drawable.ic_capsule,
            contentDescription = "약",
            caption = "약",
            color = Color(0xffFFC711),
            isActive = true
        ),
        IconUIModel(
            iconResId = R.drawable.ic_pen,
            contentDescription = "접종",
            caption = "접종",
            color = Color(0xff6D6D6D),
            isActive = false
        ),
        IconUIModel(
            iconResId = R.drawable.ic_heart,
            contentDescription = "병원",
            caption = "병원",
            color = Color(0xffF49393),
            isActive = true
        ),
        IconUIModel(
            iconResId = R.drawable.ic_edit_box,
            contentDescription = "메모",
            caption = "메모",
            color = Color(0xffC109C1),
            isActive = false
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        iconItems.forEach { item ->
            IconWithCaption(item)
        }
    }
}

@Composable
fun ExpandableCardSection() {
    // 카테고리 리스트 정의
    val categories = listOf(
        "피부 질환 검사" to Color(0xFFFFF1C1),
        "목욕" to Color(0xFFE1F5FE),
        "산책" to Color(0xFFDCEDC8),
        "간식" to Color(0xFFFCE4EC),
        "약" to Color(0xFFFFFBCB),
        "접종" to Color(0xFFA5A5A5),
        "병원" to Color(0xFFF5AFAF),
        "메모" to Color(0xFFCA9ECA),
    )

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .background(color = Color.White) // 배경색 설정
    ) {
        items(categories) { (title, color) ->
            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                // 타이틀 추가
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp),
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 12.dp),
                )
                ExpandableCard(color = color)
            }
        }
    }
}

@Composable
fun ExpandableCard(color: Color) {
    // 둥근 모서리를 위해 large shape 사용
    Card(
        Modifier
            .fillMaxWidth()
            .background(color = color, shape = RoundedCornerShape(44.dp))
            .clickable { /* 클릭 시 동작 처리 */ }
    ) {
        // Row 내부 요소를 세로 중심에 정렬
        Row(
            modifier = Modifier
                .background(color = color)
                .padding(12.dp, 4.dp)
                .fillMaxWidth()
                .toggleable(value = true, onValueChange = {
                    Modifier.height(200.dp)
                }),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically // 요소 세로 중앙 정렬
        ) {
            // '기록이 없습니다' 텍스트
            Text(
                text = "기록이 없습니다",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black // 가시성을 위해 필요시 조정
            )
            // '기록 추가' 버튼
            TextButton(
                onClick = { /* 기록 추가 동작 처리 */ }
            ) {
                Text("기록 추가", color = Color.Black, fontWeight = FontWeight.Bold)
            }
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
