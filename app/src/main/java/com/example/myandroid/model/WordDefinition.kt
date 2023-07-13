package com.example.myandroid.model

import com.example.myandroid.database.entity.WordDefinitionEntity
import java.util.*

data class WordDefinition(
    val word: String,
    val definitions: List<String>,
    val registered: Boolean,
    val registeredAt: Date?
) {
    fun asWordDefinitionEntity(): WordDefinitionEntity {
        return WordDefinitionEntity(
            word, definitions, registered, registeredAt
        )
    }
}
