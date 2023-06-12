package com.example.myandroid.ui.counter

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myandroid.data.CounterRepository
import com.example.myandroid.data.toCounterUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val COUNTER_LIMIT = 99

class CounterViewModel(
    private val counterRepository: CounterRepository
) : ViewModel() {

    private val _counterState = MutableStateFlow<CounterState>(CounterState.Loading)
    val counterState = _counterState.asStateFlow()


    init {
        viewModelScope.launch {
            counterRepository.getAll().collect() { counters ->
                _counterState.value = CounterState.Success(counters.map { it.toCounterUiState() })
            }
        }
    }

    fun insertCounterCard(text: String) {
        viewModelScope.launch {
            counterRepository.insert(
                com.example.myandroid.data.Counter(
                    id = 0,
                    title = text,
                    counter = 0
                )
            )
        }
    }

    fun deleteCounterCard(id: Int) {
        viewModelScope.launch {
            counterRepository.deleteById(id)
        }
    }

    fun incrementCounter(id: Int) {
        viewModelScope.launch {
            val currentState = _counterState.value
            if (currentState is CounterState.Success) {
                counterRepository.incrementCounterById(id)
            }
        }
    }

    fun decrementCounter(id: Int) {
        viewModelScope.launch {
            val currentState = _counterState.value
            if (currentState is CounterState.Success) {
                counterRepository.decrementCounterById(id)
            }
        }
    }
}