package com.example.myandroid.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myandroid.model.WordDefinition
import java.util.*

@Entity(tableName = "word_definitions")
data class WordDefinitionEntity(
    @PrimaryKey
    val word : String,
    val definitions : List<String>,
    val registered : Boolean,
    val registeredAt : Date?
)

fun WordDefinitionEntity.asExternalModel() = WordDefinition(
    word, definitions, registered, registeredAt
)
