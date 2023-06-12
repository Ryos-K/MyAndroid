package com.example.myandroid.ui.theme.counter

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val COUNTER_LIMIT = 99

class CounterViewModel : ViewModel() {

    private val _counterState = MutableStateFlow<CounterState>(CounterState.Loading)
    val counterState = _counterState.asStateFlow()


    init {
        getCounterCards()
    }

    private fun getCounterCards() {
        viewModelScope.launch {
            delay(2_000)
            val mockCounterCards = listOf(
                CounterCardUiState(1, "Counter 1", 0),
                CounterCardUiState(2, "Counter 2", 2),
                CounterCardUiState(3, "Counter 3", 4),
                CounterCardUiState(4, "Counter 4", 10),
                CounterCardUiState(5, "Counter 5", 40),
                CounterCardUiState(6, "Counter 6", 50),
                CounterCardUiState(7, "Counter 7", 60),
                CounterCardUiState(8, "Counter 8", 70),
            )
            _counterState.value = CounterState.Success(mockCounterCards)
        }
    }

    fun deleteCounterCard(id: Int) {
        viewModelScope.launch {
            val currentState = _counterState.value
            if (currentState is CounterState.Success) {
                val updatedList = currentState.counterCards.filter { it.id != id }
                _counterState.update { CounterState.Success(updatedList) }
            }
        }
    }

    fun incrementCounter(id: Int) {
        viewModelScope.launch {
            val currentState = _counterState.value
            if (currentState is CounterState.Success) {
                val updatedList = currentState.counterCards.map { card -> card.copy() }
                updatedList.find { card -> card.id == id }
                    ?.also { if (it.counter < COUNTER_LIMIT) it.counter++ }

                _counterState.update { CounterState.Success(updatedList) }
            }
        }
    }

    fun decrementCounter(id: Int) {
        viewModelScope.launch {
            val currentState = _counterState.value
            if (currentState is CounterState.Success) {
                val updatedList = currentState.counterCards.map { card -> card.copy() }
                updatedList.find { card -> card.id == id }
                    ?.also { if (it.counter > 0) it.counter-- }

                _counterState.update { CounterState.Success(updatedList) }
            }
        }
    }
}