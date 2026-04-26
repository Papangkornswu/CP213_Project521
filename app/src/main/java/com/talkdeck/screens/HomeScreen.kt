package com.talkdeck.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(onStartGame: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "TalkDeck",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 48.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Conversation Game",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 48.dp)
        )

        Button(
            onClick = onStartGame,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Start Game", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        
        OutlinedButton(
            onClick = { /* TODO Settings */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Settings")
        }

        OutlinedButton(
            onClick = { /* TODO How To Play */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("How to Play")
        }
    }
}
