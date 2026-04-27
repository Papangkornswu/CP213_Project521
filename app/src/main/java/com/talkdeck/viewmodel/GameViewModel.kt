package com.talkdeck.viewmodel

import androidx.lifecycle.ViewModel
import com.talkdeck.data.Card
import com.talkdeck.data.Deck
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

    private val _customDecks = MutableStateFlow<List<Deck>>(emptyList())
    val customDecks: StateFlow<List<Deck>> = _customDecks.asStateFlow()

    fun startGame(selectedDeck: Deck) {
        val finalDeck = selectedDeck.cards.shuffled()

        // Since we removed player selection, we just have a generic player list, 
        // or actually we don't need players anymore since the indicator is removed.
        val names = listOf("Player 1", "Player 2")

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

    fun addCustomDeck(deck: Deck) {
        _customDecks.update { it + deck }
    }
}
