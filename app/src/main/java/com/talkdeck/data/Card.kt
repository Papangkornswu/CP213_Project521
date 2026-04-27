package com.talkdeck.data

data class Card(
    val id: Int,
    val type: CardType,
    val content: String
)

data class Deck(
    val id: String,
    val name: String,
    val cards: List<Card>
)

enum class CardType { QUESTION, ACTION }
