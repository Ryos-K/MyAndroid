package com.example.myandroid.data

import com.example.myandroid.data.local.WordDefinitionDao
import com.example.myandroid.data.local.WordDefinitionEntity
import com.example.myandroid.data.local.asExternalModel
import com.example.myandroid.data.remote.DictionaryService
import com.example.myandroid.model.WordDefinition
import com.example.myandroid.utils.MyResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WordInfoRepository(
    private val dao: WordDefinitionDao,
    private val service: DictionaryService
) {
    suspend fun getWordDefinition(word: String): WordDefinition {
        return withContext(Dispatchers.IO) {
            return@withContext dao.getByWord(word)?.asExternalModel() ?: try {
                val wordInfo = service.getWordInfo(word).first()
                dao.upsert(wordInfo.toWordDefinition().asWordDefinitionEntity())
                wordInfo.toWordDefinition()
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun observeAll() : Flow<List<WordDefinition>> = dao.getAll().map { it.map { entity -> entity.asExternalModel() } }

    fun observeRegistered() : Flow<List<WordDefinition>> = dao.getRegistered().map {it.map { entity -> entity.asExternalModel() }}

    suspend fun delete(wordDefinitionEntity: WordDefinitionEntity) = dao.delete(wordDefinitionEntity)

    suspend fun upsert(wordDefinitionEntity: WordDefinitionEntity) = dao.upsert(wordDefinitionEntity)


}