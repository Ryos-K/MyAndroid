package com.example.myandroid.ui.counter

import com.example.myandroid.data.Counter

data class CounterUiState(
    val id: Int,
    var title: String,
    var counter: Int,
)

sealed class CounterState() {
    object Loading : CounterState()
    data class Success(val counterCards: List<CounterUiState>) : CounterState()
    object Error : CounterState()
}

fun CounterUiState.toCounter() : Counter{
    return Counter(
        id = this.id,
        title = this.title,
        counter = this.counter,
    )
}