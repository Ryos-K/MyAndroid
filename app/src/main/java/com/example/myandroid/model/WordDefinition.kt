package com.example.myandroid.model

import com.example.myandroid.data.local.WordDefinitionEntity

data class WordDefinition(
    val word: String,
    val definitions: List<String>,
) {
    fun asWordDefinitionEntity(): WordDefinitionEntity {
        return WordDefinitionEntity(
            word, definitions
        )
    }
}
