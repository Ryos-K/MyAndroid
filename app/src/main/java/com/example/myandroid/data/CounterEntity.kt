package com.example.myandroid.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myandroid.model.Counter

@Entity(tableName = "counters")
data class CounterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String = "",
    val count: Int = 0,
)

fun CounterEntity.asExternalModel() = Counter(id, title, count)