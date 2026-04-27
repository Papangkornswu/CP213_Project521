package com.talkdeck.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkdeck.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(viewModel: GameViewModel, onEndGame: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isGameOver) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("จบเกม!", style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(8.dp))
            Text("คุณเล่นการ์ดจนหมดกองแล้ว 🎉", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onEndGame,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) { 
                Text("กลับหน้าแรก", fontSize = 18.sp, fontWeight = FontWeight.Bold) 
            }
        }
        return
    }

    val currentCard = uiState.currentCard
    val currentPlayer = uiState.players[uiState.currentPlayerIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "การ์ดที่เหลือ: ${uiState.deck.size}", 
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.weight(1f))

        FlipCard(
            isFlipped = uiState.isFlipped,
            onFlip = { viewModel.flipCard() },
            onNext = { viewModel.nextTurn() },
            front = {
                CardFront()
            },
            back = {
                CardBack(
                    cardType = if (currentCard?.type?.name == "QUESTION") "คำถาม" else "คำสั่ง", 
                    content = currentCard?.content ?: ""
                )
            }
        )

        Spacer(modifier = Modifier.weight(1f))
        
        // Remove old buttons, add hint text
        Text(
            text = if (!uiState.isFlipped) "แตะการ์ดเพื่อเปิด" else "แตะการ์ดเพื่อไปยังตาถัดไป",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}

@Composable
fun FlipCard(
    isFlipped: Boolean,
    onFlip: () -> Unit,
    onNext: () -> Unit,
    front: @Composable () -> Unit,
    back: @Composable () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = if (isFlipped) tween(durationMillis = 600) else androidx.compose.animation.core.snap()
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .aspectRatio(0.65f)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable { 
                if (!isFlipped) {
                    onFlip()
                } else {
                    onNext()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (rotation <= 90f) {
            front()
        } else {
            Box(Modifier.graphicsLayer { rotationY = 180f }) {
                back()
            }
        }
    }
}

@Composable
fun CardFront() {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                "TalkDeck", 
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Black, letterSpacing = (-2).sp), 
                color = MaterialTheme.colorScheme.onPrimary, 
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CardBack(cardType: String, content: String) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = cardType, 
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold, letterSpacing = 2.sp), 
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.headlineSmall.copy(lineHeight = 36.sp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
