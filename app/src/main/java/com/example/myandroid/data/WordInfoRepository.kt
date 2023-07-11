package com.example.myandroid.data

import com.example.myandroid.data.local.WordDefinitionDao
import com.example.myandroid.data.local.asExternalModel
import com.example.myandroid.data.remote.DictionaryService
import com.example.myandroid.model.WordDefinition
import com.example.myandroid.utils.MyResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
}