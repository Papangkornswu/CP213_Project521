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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.talkdeck.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(viewModel: GameViewModel, onEndGame: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isGameOver) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Game Over!", style = MaterialTheme.typography.titleLarge)
            Text("You've gone through the deck.", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onEndGame) { Text("Back to Home") }
        }
        return
    }

    val currentCard = uiState.currentCard
    val currentPlayer = uiState.players[uiState.currentPlayerIndex]

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Turn: $currentPlayer", style = MaterialTheme.typography.titleLarge)
        Text(text = "Remaining cards: ${uiState.deck.size}", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.weight(1f))

        FlipCard(
            isFlipped = uiState.isFlipped,
            onClick = { viewModel.flipCard() },
            front = {
                CardFront()
            },
            back = {
                CardBack(cardType = currentCard?.type?.name ?: "", content = currentCard?.content ?: "")
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = { viewModel.nextTurn() }) {
                Text("Skip Card")
            }
            Button(
                onClick = { viewModel.nextTurn() },
                enabled = uiState.isFlipped
            ) {
                Text("Next Turn")
            }
        }
    }
}

@Composable
fun FlipCard(
    isFlipped: Boolean,
    onClick: () -> Unit,
    front: @Composable () -> Unit,
    back: @Composable () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .aspectRatio(0.7f)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable { if (!isFlipped) onClick() },
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
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text("TalkDeck\nTap to flip", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimary, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun CardBack(cardType: String, content: String) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = cardType, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSecondary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}
