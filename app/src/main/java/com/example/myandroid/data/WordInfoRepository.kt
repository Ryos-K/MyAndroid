package com.example.myandroid.data

import com.example.myandroid.data.remote.DictionaryService
import com.example.myandroid.model.WordDefinition
import com.example.myandroid.utils.MyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WordInfoRepository(
    val service: DictionaryService
) {
    suspend fun getWordDefinition(word: String) : WordDefinition {
        return service.getWordInfo(word).first().toWordDefinition()
    }
}