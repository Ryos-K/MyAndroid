package com.example.myandroid.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myandroid.ui.counter.CounterUiState

@Entity(tableName = "counters")
data class Counter(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String = "",
    val counter: Int = 0,
)

fun Counter.toCounterUiState() : CounterUiState {
    return CounterUiState(
        id = this.id,
        title = this.title,
        counter = this.counter,
    )
}