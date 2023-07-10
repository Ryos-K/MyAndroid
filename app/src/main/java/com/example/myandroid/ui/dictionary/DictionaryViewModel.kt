package com.example.myandroid.ui.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myandroid.data.WordInfoRepository
import com.example.myandroid.model.WordDefinition
import com.example.myandroid.utils.MyResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DictionaryViewModel(
    private val wordInfoRepository: WordInfoRepository
) : ViewModel() {

    private val _wordDefinitionState = MutableStateFlow<MyResult<WordDefinition>>(MyResult.Loading())
    val wordDefinitionState = _wordDefinitionState.asStateFlow()

    fun loadWordDefinition(word: String) {
        viewModelScope.launch {
            _wordDefinitionState.value = MyResult.Loading()
            try {
                val wordDefinition = wordInfoRepository.getWordDefinition(word)
                _wordDefinitionState.value = MyResult.Success(wordDefinition)
            } catch (e: Exception) {
                _wordDefinitionState.value = MyResult.Error(e)
            }
        }
    }
}