package com.example.myandroid.database.dao

import androidx.room.*
import com.example.myandroid.database.entity.WordDefinitionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDefinitionDao {
    @Query("SELECT * FROM word_definitions")
    fun getAll(): Flow<List<WordDefinitionEntity>>

    @Query("SELECT * FROM word_definitions WHERE word = :word")
    fun getByWord(word: String): WordDefinitionEntity?

    @Query("SELECT * FROM word_definitions WHERE registered = true")
    fun getRegistered() : Flow<List<WordDefinitionEntity>>

    @Upsert
    suspend fun upsert(wordDefinitionEntity: WordDefinitionEntity)

    @Query("SELECT EXISTS(SELECT * FROM word_definitions WHERE word = :word)")
    suspend fun exist(word: String): Boolean

    @Delete
    suspend fun delete(wordDefinitionEntity: WordDefinitionEntity)
}