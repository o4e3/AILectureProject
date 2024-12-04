package com.rtl.petkinfe.presentation.view.pet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rtl.petkinfe.R

@Composable
fun PetRegistrationScreen(navController: NavController, viewModel: PetRegistrationViewModel = hiltViewModel()) {
    // 뷰모델에서 상태 가져오기
    val petName by viewModel.petName.collectAsState()
    val petAge by viewModel.petAge.collectAsState()
    val petGender by viewModel.petGender.collectAsState()
    val petSpecies by viewModel.petSpecies.collectAsState()
    val petBreed by viewModel.petBreed.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(top = 80.dp), // 상단 여백 추가
        verticalArrangement = Arrangement.spacedBy(8.dp) // 요소 간 간격 설정
    ) {
        // 헤더 텍스트
        Text("안녕하세요!", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        Text("반려동물을 등록해주세요 🐕", style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp), fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        Text("기본 정보", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        // 반려동물 이름 입력
        OutlinedTextField(
            value = petName,
            onValueChange = { viewModel.updatePetName(it) },
            label = { Text("반려동물 이름") },
            modifier = Modifier.fillMaxWidth()
        )

        // 반려동물 나이 입력
        OutlinedTextField(
            value = petAge,
            onValueChange = { viewModel.updatePetAge(it) },
            label = { Text("반려동물 나이") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))


        // 반려동물 성별 선택
        Text("성별", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButtonWithLabel("수컷           ", petGender, onOptionSelected = { viewModel.updatePetGender(it) })
                RadioButtonWithLabel("암컷     ", petGender, onOptionSelected = { viewModel.updatePetGender(it) })
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButtonWithLabel("중성화 수컷", petGender, onOptionSelected = { viewModel.updatePetGender(it) })
                RadioButtonWithLabel("중성화 암컷", petGender, onOptionSelected = { viewModel.updatePetGender(it) })
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // 종 선택
        Text("종", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            RadioButtonWithLabel("강아지", petSpecies, onOptionSelected = { viewModel.updatePetSpecies(it) })
            RadioButtonWithLabel("고양이", petSpecies, onOptionSelected = { viewModel.updatePetSpecies(it) })
        }
        // 종 선택
        Text("품종 ex) 말티즈", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        // 품종 입력
        OutlinedTextField(
            value = petBreed,
            onValueChange = { viewModel.updatePetBreed(it) },
            label = { Text("품종 (선택)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 등록 버튼
        Button(
            onClick = {
                viewModel.registerPet() // 반려동물 등록 처리
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("등록하기")
        }
    }
}

@Composable
fun RadioButtonWithLabel(label: String, selectedOption: String, onOptionSelected: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 12.dp) // 버튼 간 간격 설정
    ) {
        RadioButton(
            selected = selectedOption == label,
            onClick = { onOptionSelected(label) }
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
