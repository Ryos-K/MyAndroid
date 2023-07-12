package com.example.myandroid.ui.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myandroid.data.WordInfoRepository
import com.example.myandroid.model.WordDefinition
import com.example.myandroid.utils.MyResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*

class DictionaryViewModel(
    private val wordInfoRepository: WordInfoRepository
) : ViewModel() {

    private val _searchedState = MutableStateFlow<MyResult<WordDefinition>>(MyResult.Loading())
    val searchedState = _searchedState.asStateFlow()

    val historyState = wordInfoRepository.observeRegistered()

    fun loadWordDefinition(word: String) {
        viewModelScope.launch {
            _searchedState.value = MyResult.Loading()
            try {
                val wordDefinition = wordInfoRepository.getWordDefinition(word)
                _searchedState.value = MyResult.Success(wordDefinition)
            } catch (e: Exception) {
                _searchedState.value = MyResult.Error(e)
            }
        }
    }

    fun register(wordDefinition: WordDefinition) {
        viewModelScope.launch {
            wordInfoRepository.upsert(
                wordDefinition.copy(
                    registered = true,
                    registeredAt = Date()
                ).asWordDefinitionEntity()
            )
        }
    }

    fun unregister(wordDefinition: WordDefinition) {
        viewModelScope.launch {
            wordInfoRepository.upsert(
                wordDefinition.copy(
                    registered = false,
                    registeredAt = null
                ).asWordDefinitionEntity()
            )
        }
    }
}