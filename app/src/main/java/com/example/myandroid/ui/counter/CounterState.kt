package com.example.myandroid.ui.counter

data class CounterCardUiState(
    val id: Int,
    var title: String,
    var counter: Int,
)

sealed class CounterState() {
    object Loading : CounterState()
    data class Success(val counterCards: List<CounterCardUiState>) : CounterState()
    object Error : CounterState()
}