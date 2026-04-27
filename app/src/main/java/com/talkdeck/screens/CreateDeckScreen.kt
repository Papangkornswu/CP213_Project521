package com.talkdeck.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkdeck.data.Card
import com.talkdeck.data.CardType
import com.talkdeck.data.Deck
import com.talkdeck.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDeckScreen(
    viewModel: GameViewModel,
    onBack: () -> Unit,
    onDeckCreated: () -> Unit
) {
    var deckName by remember { mutableStateOf("") }
    var cards by remember { mutableStateOf(listOf<Card>()) }
    var currentCardContent by remember { mutableStateOf("") }
    var currentCardType by remember { mutableStateOf(CardType.QUESTION) }
    var bulkText by remember { mutableStateOf("") }
    var bulkCardType by remember { mutableStateOf(CardType.QUESTION) }
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("สร้างชุดคำถาม", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.padding(start = 8.dp)) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "กลับ", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            if (deckName.isNotBlank() && cards.isNotEmpty()) {
                                viewModel.addCustomDeck(
                                    Deck(
                                        id = "custom_${System.currentTimeMillis()}",
                                        name = deckName,
                                        cards = cards
                                    )
                                )
                                onDeckCreated()
                            }
                        },
                        enabled = deckName.isNotBlank() && cards.isNotEmpty(),
                        modifier = Modifier.padding(end = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("บันทึก", fontWeight = FontWeight.Bold)
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
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = deckName,
                onValueChange = { deckName = it },
                label = { Text("ชื่อชุดคำถาม") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column {
                    TabRow(selectedTabIndex = selectedTabIndex) {
                        Tab(
                            selected = selectedTabIndex == 0,
                            onClick = { selectedTabIndex = 0 },
                            text = { Text("เพิ่มทีละใบ") }
                        )
                        Tab(
                            selected = selectedTabIndex == 1,
                            onClick = { selectedTabIndex = 1 },
                            text = { Text("วางข้อความ (หลายใบ)") }
                        )
                    }

                    if (selectedTabIndex == 0) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            OutlinedTextField(
                                value = currentCardContent,
                                onValueChange = { currentCardContent = it },
                                label = { Text("ข้อความบนการ์ด") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FilterChip(
                                    selected = currentCardType == CardType.QUESTION,
                                    onClick = { currentCardType = CardType.QUESTION },
                                    label = { Text("คำถาม") }
                                )
                                FilterChip(
                                    selected = currentCardType == CardType.ACTION,
                                    onClick = { currentCardType = CardType.ACTION },
                                    label = { Text("คำสั่ง (ทำ)") }
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Button(
                                    onClick = {
                                        if (currentCardContent.isNotBlank()) {
                                            val newCard = Card(
                                                id = cards.size + 1,
                                                type = currentCardType,
                                                content = currentCardContent.trim()
                                            )
                                            cards = cards + newCard
                                            currentCardContent = ""
                                        }
                                    },
                                    enabled = currentCardContent.isNotBlank()
                                ) {
                                    Text("เพิ่ม")
                                }
                            }
                        }
                    } else {
                        Column(modifier = Modifier.padding(16.dp)) {
                            OutlinedTextField(
                                value = bulkText,
                                onValueChange = { bulkText = it },
                                label = { Text("วางข้อความ (1 บรรทัด = 1 การ์ด)") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                maxLines = 10
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FilterChip(
                                    selected = bulkCardType == CardType.QUESTION,
                                    onClick = { bulkCardType = CardType.QUESTION },
                                    label = { Text("คำถาม") }
                                )
                                FilterChip(
                                    selected = bulkCardType == CardType.ACTION,
                                    onClick = { bulkCardType = CardType.ACTION },
                                    label = { Text("คำสั่ง (ทำ)") }
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Button(
                                    onClick = {
                                        if (bulkText.isNotBlank()) {
                                            val lines = bulkText.split("\n").filter { it.isNotBlank() }
                                            val newCards = lines.mapIndexed { index, text ->
                                                Card(
                                                    id = cards.size + index + 1,
                                                    type = bulkCardType,
                                                    content = text.trim()
                                                )
                                            }
                                            cards = cards + newCards
                                            bulkText = ""
                                            selectedTabIndex = 0
                                        }
                                    },
                                    enabled = bulkText.isNotBlank()
                                ) {
                                    Text("นำเข้า")
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("การ์ดในชุดนี้ (${cards.size} ใบ)", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cards) { card ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (card.type == CardType.QUESTION) "คำถาม" else "คำสั่ง",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(card.content)
                            }
                            IconButton(onClick = {
                                cards = cards.filter { it.id != card.id }
                            }) {
                                Icon(Icons.Filled.Delete, contentDescription = "ลบ", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }
}
