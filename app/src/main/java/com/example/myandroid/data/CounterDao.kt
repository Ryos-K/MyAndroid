package com.example.myandroid.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CounterDao {
    @Query("SELECT * from counters")
    fun getAll() : Flow<List<CounterEntity>>

    @Insert
    suspend fun insert(counterEntity: CounterEntity)

    @Update
    suspend fun update(counterEntity: CounterEntity)

    @Query("DELETE FROM counters WHERE id = :CounterId")
    suspend fun deleteById(CounterId: Int): Int
}