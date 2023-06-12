package com.example.myandroid.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterDao {
    @Query("SELECT * from counters")
    fun getAll() : Flow<List<Counter>>

    @Insert
    suspend fun insert(counter: Counter)

    @Update
    suspend fun update(counter: Counter)

    @Query("DELETE FROM counters WHERE id = :CounterId")
    suspend fun deleteById(CounterId: Int): Int

    @Query("UPDATE counters SET counter = counter + 1 WHERE id = :CounterId")
    suspend fun incrementCounterById(CounterId: Int) : Int

    @Query("UPDATE counters SET counter = counter - 1 WHERE id = :CounterId")
    suspend fun decrementCounterById(CounterId: Int) : Int
}