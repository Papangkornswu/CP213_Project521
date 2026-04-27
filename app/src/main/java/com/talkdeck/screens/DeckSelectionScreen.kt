package com.talkdeck.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkdeck.data.Deck
import com.talkdeck.data.SampleData
import com.talkdeck.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeckSelectionScreen(
    viewModel: GameViewModel,
    onGameStarted: () -> Unit,
    onBack: () -> Unit
) {
    val customDecks by viewModel.customDecks.collectAsState()
    val allDecks = SampleData.preMadeDecks + customDecks

    var showAddDeckDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("เลือกชุดคำถาม", fontWeight = FontWeight.Bold) },
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDeckDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "เพิ่มชุดคำถาม")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(allDecks) { deck ->
                    DeckCard(deck = deck, onClick = {
                        viewModel.startGame(deck)
                        onGameStarted()
                    })
                }
            }
        }
    }

    if (showAddDeckDialog) {
        // Simplified Add Deck dialog. In a real app, this would be a separate screen
        // to manage cards within the deck.
        var newDeckName by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddDeckDialog = false },
            title = { Text("เพิ่มชุดคำถามใหม่") },
            text = {
                OutlinedTextField(
                    value = newDeckName,
                    onValueChange = { newDeckName = it },
                    label = { Text("ชื่อชุดคำถาม") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newDeckName.isNotBlank()) {
                        viewModel.addCustomDeck(
                            Deck(
                                id = "custom_${System.currentTimeMillis()}",
                                name = newDeckName,
                                cards = emptyList() // User would add cards here in a full implementation
                            )
                        )
                        showAddDeckDialog = false
                    }
                }) {
                    Text("สร้าง")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDeckDialog = false }) {
                    Text("ยกเลิก")
                }
            }
        )
    }
}

@Composable
fun DeckCard(deck: Deck, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = deck.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${deck.cards.size} ใบ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}
