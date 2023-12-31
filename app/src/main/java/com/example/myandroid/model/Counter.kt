package com.example.myandroid.model

import com.example.myandroid.database.entity.CounterEntity


data class Counter(
    val id: Int,
    val title: String,
    val count: Int,
) {
    fun asEntity() : CounterEntity = CounterEntity(id, title, count)
}