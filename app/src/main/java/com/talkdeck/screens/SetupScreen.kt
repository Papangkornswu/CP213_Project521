package com.talkdeck.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.talkdeck.data.Category
import com.talkdeck.data.Depth
import com.talkdeck.viewmodel.GameViewModel

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
                title = { Text("Setup Game") },
                navigationIcon = {
                    Button(onClick = onBack, modifier = Modifier.padding(start = 8.dp)) { Text("Back") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Text("Players: ${playerCount.toInt()}", style = MaterialTheme.typography.titleMedium)
            Slider(
                value = playerCount,
                onValueChange = { playerCount = it },
                valueRange = 2f..8f,
                steps = 5
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            Text("Category", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(vertical = 8.dp)) {
                Category.values().forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = if (selectedCategory == cat) null else cat },
                        label = { Text(cat.name) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Depth", style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(vertical = 8.dp)) {
                Depth.values().forEach { depth ->
                    FilterChip(
                        selected = selectedDepth == depth,
                        onClick = { selectedDepth = if (selectedDepth == depth) null else depth },
                        label = { Text(depth.name) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Include Action Cards", modifier = Modifier.weight(1f))
                Switch(checked = includeActions, onCheckedChange = { includeActions = it })
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.startGame(selectedCategory, selectedDepth, includeActions, playerCount.toInt())
                    onGameStarted()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Start")
            }
        }
    }
}
