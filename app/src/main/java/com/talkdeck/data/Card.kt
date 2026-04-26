package com.talkdeck.data

data class Card(
    val id: Int,
    val type: CardType,
    val category: Category,
    val depth: Depth,
    val content: String
)

enum class CardType { QUESTION, ACTION }
enum class Category { FUN, PERSONAL, DEEP, RANDOM }
enum class Depth { LIGHT, MEDIUM, DEEP }
