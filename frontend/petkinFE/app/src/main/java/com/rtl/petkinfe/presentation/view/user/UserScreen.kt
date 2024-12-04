package com.rtl.petkinfe.presentation.view.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.rtl.petkinfe.R
import com.rtl.petkinfe.domain.model.Pet
import com.rtl.petkinfe.utils.formatDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    navController: NavHostController,
    viewModel: UserViewModel = hiltViewModel()
) {
    val latestPet by viewModel.latestPet.collectAsState()
    val userInfo = viewModel.userInfo.collectAsState()

    Scaffold(
        contentColor = Color.LightGray,
        topBar = {
            TopAppBar(
                title = { Text("마이페이지") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Navigation drawer or back action */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            UserInfoSection(name = userInfo.value.nickname, email = userInfo.value.email) {
            }
            if (latestPet != null) {
                    PetInfoSection(pet = latestPet!!, onEditClick = { updatedPet ->
                            viewModel.updatePet(updatedPet)
                    })
            } else {
                Text("등록된 반려동물이 없습니다.", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun PetInfoSection(pet: Pet, onEditClick: (Pet) -> Unit) {
    var isEditing by remember { mutableStateOf(false) }

    if (isEditing) {
        EditPetDialog(pet = pet, onDismiss = { isEditing = false }, onSave = { updatedPet ->
            isEditing = false
            onEditClick(updatedPet)
        })
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
            Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                    Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                    ) {
                            PetIcon(pet)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                    text = pet.name,
                                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                    fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = { isEditing = true }) {
                                    Icon(
                                            imageVector = Icons.Filled.Edit,
                                            contentDescription = "Edit Pet",
                                            tint = MaterialTheme.colorScheme.primary
                                    )
                            }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))

                    InfoRow(label = "종", value = if (pet.species == "dog") "강아지 🐕" else "고양이 🐈")
                    InfoRow(label = "품종", value = pet.breed)
                    InfoRow(label = "나이", value = "${pet.age} 살")
                    InfoRow(label = "성별", value = if (pet.gender == "M") "수컷" else "암컷")
                    pet.registerDate?.let { InfoRow(label = "등록 날짜", value = formatDate(it)) }
            }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
fun EditPetDialog(
    pet: Pet,
    onDismiss: () -> Unit,
    onSave: (Pet) -> Unit
) {
    var name by remember { mutableStateOf(pet.name) }
    var breed by remember { mutableStateOf(pet.breed) }
    var age by remember { mutableStateOf(pet.age.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onSave(
                    pet.copy(
                        name = name,
                        breed = breed,
                        age = age.toIntOrNull() ?: pet.age
                    )
                )
            }) {
                Text("저장")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        },
        title = { Text("반려동물 수정") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("이름") }
                )
                OutlinedTextField(
                    value = breed,
                    onValueChange = { breed = it },
                    label = { Text("품종") }
                )
                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("나이") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun PetIcon(pet: Pet) {
    val imageRes = if (pet.species == "dog") R.drawable.res_dog else R.drawable.res_cat

    AsyncImage(
        model = imageRes,
        contentDescription = "Pet Icon",
        modifier = Modifier.size(48.dp),
        placeholder = null
    )
}

@Composable
fun UserInfoSection(
    name: String,
    email: String,
    onLogout: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = "https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Smiling%20Face%20with%20Sunglasses.png",
                    contentDescription = "Profile Icon",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "내 정보",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp
                )
            }
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))

            Spacer(modifier = Modifier.height(8.dp))

            InfoRow(label = "이름", value = name)
            InfoRow(label = "이메일", value = email)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* TODO: Implement logout */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("로그아웃", color = Color.White)
            }
        }
    }
}