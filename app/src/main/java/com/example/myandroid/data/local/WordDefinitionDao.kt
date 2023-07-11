package com.example.myandroid.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDefinitionDao {
    @Query("SELECT * FROM word_definitions")
    fun getAll(): Flow<List<WordDefinitionEntity>>

    @Query("SELECT * FROM word_definitions WHERE word = :word")
    fun getByWord(word: String): WordDefinitionEntity?

    @Upsert
    fun upsert(wordDefinitionEntity: WordDefinitionEntity)

    @Query("SELECT EXISTS(SELECT * FROM word_definitions WHERE word = :word)")
    fun exist(word: String): Boolean
}