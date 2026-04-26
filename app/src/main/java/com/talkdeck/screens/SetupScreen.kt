package com.talkdeck.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkdeck.data.Category
import com.talkdeck.data.Depth
import com.talkdeck.viewmodel.GameViewModel

fun Category.toThai(): String = when(this) {
    Category.FUN -> "สนุกสนาน"
    Category.PERSONAL -> "เรื่องส่วนตัว"
    Category.DEEP -> "ลึกซึ้ง"
    Category.RANDOM -> "สุ่ม"
}

fun Depth.toThai(): String = when(this) {
    Depth.LIGHT -> "ชิลๆ"
    Depth.MEDIUM -> "ปานกลาง"
    Depth.DEEP -> "จริงจัง"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupScreen(
    viewModel: GameViewModel,
    onGameStarted: () -> Unit,
    onBack: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var selectedDepth by remember { mutableStateOf<Depth?>(null) }
    var includeActions by remember { mutableStateOf(true) }
    var playerCount by remember { mutableStateOf(2f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ตั้งค่าเกม", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    TextButton(onClick = onBack, modifier = Modifier.padding(start = 8.dp)) { 
                        Text("กลับ", color = MaterialTheme.colorScheme.primary, fontSize = 16.sp) 
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Text("ผู้เล่น: ${playerCount.toInt()} คน", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Slider(
                value = playerCount,
                onValueChange = { playerCount = it },
                valueRange = 2f..8f,
                steps = 5,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary
                )
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            Text("หมวดหมู่การสนทนา", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Category.values().forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = if (selectedCategory == cat) null else cat },
                        label = { Text(cat.toThai()) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("ระดับความลึก", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Depth.values().forEach { depth ->
                    FilterChip(
                        selected = selectedDepth == depth,
                        onClick = { selectedDepth = if (selectedDepth == depth) null else depth },
                        label = { Text(depth.toThai()) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("รวมการ์ดคำสั่ง (Action Cards)", modifier = Modifier.weight(1f), fontSize = 16.sp)
                Switch(
                    checked = includeActions, 
                    onCheckedChange = { includeActions = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                        checkedTrackColor = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.startGame(selectedCategory, selectedDepth, includeActions, playerCount.toInt())
                    onGameStarted()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("เริ่มเล่นเลย!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
