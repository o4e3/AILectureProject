package com.rtl.petkinfe.presentation.view.login

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rtl.petkinfe.R
import com.rtl.petkinfe.navigation.PetkinScreens
import com.rtl.petkinfe.ui.theme.SplashBackgroundColor


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val viewModel: LoginViewModel = hiltViewModel()
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()
    val systemUiController = rememberSystemUiController()

    // 상태 표시줄 숨기거나 투명 처리
    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = SplashBackgroundColor, // 상태 표시줄 색상 (투명도 조정 가능)
            darkIcons = false // 상태 표시줄 아이콘 색상 (true = 어두운 아이콘, false = 밝은 아이콘)
        )
    }

    Scaffold(
        containerColor = SplashBackgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SplashBackgroundColor) // Set background color
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 3D 아이콘 표시
            Image(
                painter = painterResource(R.drawable.splash_3d),
                contentDescription = "3D Icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp)) // 아이콘과 버튼 사이의 간격

            // 카카오 로그인 버튼
            Image(
                painter = painterResource(R.drawable.btn_kakao),
                contentDescription = "Kakao Login",
                modifier = Modifier
                    .size(width = 330.dp, height = 64.dp)
                    .clickable {
                        // 카카오 로그인 로직 추가
                        viewModel.loginWithKakao(context)
                    }
            )
        }
    }

    when (loginState) {
        is LoginViewModel.LoginState.Success -> {
            onLoginSuccess()
        }
        is LoginViewModel.LoginState.Failure -> {
            Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }

}
