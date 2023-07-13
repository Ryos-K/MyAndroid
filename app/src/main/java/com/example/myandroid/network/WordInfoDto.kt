package com.example.myandroid.network

import com.example.myandroid.model.WordDefinition

data class WordInfoDto(
    val word: String,
    val meanings: List<MeaningDto>
) {
    fun toWordDefinition() : WordDefinition {
        return WordDefinition(
            word = this.word,
            definitions = this.meanings.map {
                it.definitions.map { definitionDto -> definitionDto.definition}
            }.flatten(),
            registered = false,
            registeredAt = null
        )
    }
}

data class MeaningDto(
    val definitions: List<DefinitionDto>
)

data class DefinitionDto(
    val definition: String,
    val example: String?
)