package com.example.myandroid.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myandroid.model.WordDefinition

@Entity(tableName = "word_definitions")
data class WordDefinitionEntity(
    @PrimaryKey
    val word : String,
    val definitions : String,
)

fun WordDefinitionEntity.asExternalModel() = WordDefinition(
    word, listOf( definitions)
)
