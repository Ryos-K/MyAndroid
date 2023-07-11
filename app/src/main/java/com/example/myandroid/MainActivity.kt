package com.example.myandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myandroid.data.WordInfoRepository
import com.example.myandroid.data.remote.DictionaryService
import com.example.myandroid.ui.dictionary.DictionaryScreen
import com.example.myandroid.ui.dictionary.DictionaryViewModel
import com.example.myandroid.ui.theme.MyAndroidTheme
import com.example.myandroid.utils.MyResult

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAndroidTheme {
                Surface {
                    val wordDefinitionDao = (application as MyApplication).database.wordDefinitionDao()
                    val wordInfoRepository = WordInfoRepository(wordDefinitionDao, DictionaryService.create())
                    val viewModel = DictionaryViewModel(wordInfoRepository)
                    DictionaryScreen(viewModel = viewModel)

                }
            }
        }
    }
}