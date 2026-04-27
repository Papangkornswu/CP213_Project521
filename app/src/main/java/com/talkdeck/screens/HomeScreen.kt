package com.talkdeck.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkdeck.R

@Composable
fun HomeScreen(onStartGame: () -> Unit) {
    var showHowToPlay by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "TalkDeck Logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Button(
                onClick = onStartGame,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("เริ่มเกม", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = { showHowToPlay = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onBackground)
            ) {
                Text("วิธีเล่น", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }

    if (showHowToPlay) {
        AlertDialog(
            onDismissRequest = { showHowToPlay = false },
            title = {
                Text(text = "วิธีการเล่น", fontWeight = FontWeight.Bold)
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text("1. ผู้เล่นรวมนั่งเป็นวงกลม", modifier = Modifier.padding(bottom = 4.dp))
                    Text("2. เลือกชุดคำถาม", modifier = Modifier.padding(bottom = 4.dp))
                    Text("3. กดเริ่มเกม", modifier = Modifier.padding(bottom = 4.dp))
                    Text("4. จั่วการ์ด 1 ใบ", modifier = Modifier.padding(bottom = 4.dp))
                    Text("5. ถ้าเป็นการ์ดคำถาม → ผู้เล่นตอบ\n    การ์ดทำ → ทำตามคำสั่งบนการ์ด", modifier = Modifier.padding(bottom = 4.dp))
                    Text("6. ส่งต่อเทิร์นไปยังผู้เล่นถัดไป", modifier = Modifier.padding(bottom = 4.dp))
                    Text("7. วนจนการ์ดนั้นหมดกอง", modifier = Modifier.padding(bottom = 4.dp))
                }
            },
            confirmButton = {
                TextButton(onClick = { showHowToPlay = false }) {
                    Text("เข้าใจแล้ว")
                }
            }
        )
    }
}
