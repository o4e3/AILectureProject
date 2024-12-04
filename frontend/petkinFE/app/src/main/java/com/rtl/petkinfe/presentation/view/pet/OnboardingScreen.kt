package com.rtl.petkinfe.presentation.view.pet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rtl.petkinfe.ui.theme.SplashBackgroundColor

@Composable
fun OnboardingScreen(
    navController: NavController, viewModel: PetRegistrationViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState() // Pager 상태 관리
    val coroutineScope = rememberCoroutineScope() // CoroutineScope 생성
    val systemUiController = rememberSystemUiController()

    // 상태 바와 내비게이션 바 숨기기
    LaunchedEffect(Unit) {
        systemUiController.isStatusBarVisible = false // 상태 바 숨기기
        systemUiController.isNavigationBarVisible = false // 내비게이션 바 숨기기
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashBackgroundColor)
            .padding(20.dp)
    ) {
        // Pager: 수평 스크롤 가능 화면
        HorizontalPager(
            count = 3, // 총 3개의 페이지
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> OnboardingPage(
                    title = "안녕하세요!",
                    description = "Petkin에 오신 것을 환영합니다. 🐕",
                    gifUrl = "https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Activities/Party%20Popper.png"
                )
                1 -> OnboardingPage(
                    title = "반려동물 등록하고\n피부 질환 예측받기",
                    description = "Petkin은 반려동물의 사진을 업로드하면\n 피부 질환을 AI로 예측할 수 있어요! 🐕🐈",
                    gifUrl = "https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Camera%20with%20Flash.png"
                )
                2 -> OnboardingPage(
                    title = "이제\n반려동물을 등록해보세요!",
                    description = " ",
                    gifUrl = "https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Animals/Dog.png"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 하단 버튼 영역
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 이전 버튼
            if (pagerState.currentPage > 0) {
                Button(onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }) {
                    Text("이전")
                }
            } else {
                Spacer(modifier = Modifier.width(64.dp)) // 첫 페이지에서는 버튼 숨김
            }

            // 다음 또는 시작하기 버튼
            Button(onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage < 2) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        // 마지막 페이지에서 반려동물 등록 화면으로 이동
                        navController.navigate("PetRegistrationScreen")
                    }
                }
            }) {
                Text(if (pagerState.currentPage < 2) "다음" else "시작하기")
            }
        }
        // 하단 공간 추가
        Spacer(modifier = Modifier.height(32.dp)) // 버튼 아래 공간 추가
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OnboardingPage(title: String, description: String, gifUrl: String) {
    Column(
        modifier = Modifier.fillMaxSize().background(SplashBackgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp, lineHeight = 36.sp),
            fontWeight = FontWeight.Bold,
            // 줄 간격 어떻게 늘림
        )
        Spacer(modifier = Modifier.height(16.dp))

        // GIF 또는 움짤 이미지 표시 (애니메이션 활성화)
        GlideImage(
            model = gifUrl,
            contentDescription = "Onboarding Animation",
            modifier = Modifier.size(150.dp), // 이미지 크기 조정
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
            color = Color.Gray
        )
    }
}
