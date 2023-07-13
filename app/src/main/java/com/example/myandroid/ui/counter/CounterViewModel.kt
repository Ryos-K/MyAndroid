package com.example.myandroid.ui.counter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myandroid.data.CounterRepository
import com.example.myandroid.model.Counter
import com.example.myandroid.utils.MyResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val COUNTER_LIMIT = 99

class CounterViewModel(
    private val counterRepository: CounterRepository
) : ViewModel() {

    private val filterText = MutableStateFlow("")

    val counterListState = counterRepository.observeAll()
        .combine<List<Counter>, String, MyResult<List<Counter>>>(filterText) { list, filter ->
            MyResult.Success(
                if (filter == "") list
                else list.filter { item -> item.title.contains(filter) }
            )
        }
        .onStart { emit(MyResult.Loading()) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = MyResult.Loading()
        )


    var shouldShowSnackbar by mutableStateOf(false)
    private var lastDeleteCounter: Counter? = null

    fun insertCounterCard(text: String) {
        viewModelScope.launch {
            counterRepository.insert(
                Counter(
                    id = 0,
                    title = text,
                    count = 0
                )
            )
        }
    }

    fun deleteCounterCard(counter: Counter) {
        viewModelScope.launch {
            counterRepository.deleteById(counter.id)
            lastDeleteCounter = counter
            shouldShowSnackbar = true
        }
    }

    fun incrementCounter(counter: Counter) {
        viewModelScope.launch {
            if (counter.count < COUNTER_LIMIT)
                counterRepository.update(counter.copy(count = counter.count + 1))
        }
    }

    fun decrementCounter(counter: Counter) {
        viewModelScope.launch {
            if (counter.count > 0)
                counterRepository.update(counter.copy(count = counter.count - 1))
        }
    }

    fun setFilterText(text: String) {
        viewModelScope.launch {
            filterText.emit(text)
        }
    }

    fun clearUndo() {
        shouldShowSnackbar = false
        lastDeleteCounter = null
    }

    fun acceptUndo() {
        shouldShowSnackbar = false
        viewModelScope.launch {
            lastDeleteCounter?.let {
                counterRepository.insert(it)
            }
        }
    }
}