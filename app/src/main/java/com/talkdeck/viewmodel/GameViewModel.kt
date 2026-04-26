package com.talkdeck.viewmodel

import androidx.lifecycle.ViewModel
import com.talkdeck.data.Card
import com.talkdeck.data.CardType
import com.talkdeck.data.Category
import com.talkdeck.data.Depth
import com.talkdeck.data.SampleData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GameState(
    val currentCard: Card? = null,
    val deck: List<Card> = emptyList(),
    val currentPlayerIndex: Int = 0,
    val players: List<String> = listOf("Player 1", "Player 2"),
    val isFlipped: Boolean = false,
    val isGameOver: Boolean = false
)

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    fun startGame(category: Category?, depth: Depth?, includeActions: Boolean, playerCount: Int) {
        val filteredCards = SampleData.cards.filter { card ->
            val isAction = card.type == CardType.ACTION
            if (isAction) {
                includeActions
            } else {
                val catMatch = category == null || card.category == category
                val depthMatch = depth == null || card.depth == depth
                catMatch && depthMatch
            }
        }.shuffled()

        // Fallback to a wider set if the resulting deck is too small (e.g. < 5 cards)
        val finalDeck = if (filteredCards.size < 5) SampleData.cards.shuffled() else filteredCards

        val names = (1..playerCount).map { "Player $it" }

        _uiState.update { 
            it.copy(
                deck = finalDeck,
                players = names,
                currentPlayerIndex = 0,
                currentCard = finalDeck.firstOrNull(),
                isFlipped = false,
                isGameOver = false
            )
        }
    }

    fun flipCard() {
        _uiState.update { it.copy(isFlipped = true) }
    }

    fun nextTurn() {
        val currentState = _uiState.value
        if (currentState.deck.size <= 1) {
            _uiState.update { it.copy(isGameOver = true, currentCard = null, deck = emptyList()) }
            return
        }

        val newDeck = currentState.deck.drop(1)
        val nextPlayer = (currentState.currentPlayerIndex + 1) % currentState.players.size

        _uiState.update { 
            it.copy(
                deck = newDeck,
                currentCard = newDeck.firstOrNull(),
                currentPlayerIndex = nextPlayer,
                isFlipped = false
            )
        }
    }
}
