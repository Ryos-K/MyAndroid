package com.example.myandroid.ui.dictionary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myandroid.data.remote.DictionaryService
import com.example.myandroid.ui.theme.MyAndroidTheme
import com.example.myandroid.utils.MyResult

@Composable
fun DictionaryScreen(
    viewModel: DictionaryViewModel
) {
    val wordDefinition = viewModel.wordDefinitionState.collectAsState().value
    Column(modifier = Modifier) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            onSearch = { text -> viewModel.loadWordDefinition(text) })
        when (wordDefinition) {
            is MyResult.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            is MyResult.Success -> {
                WordDefinitionCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    word = wordDefinition.data.word, definitions = wordDefinition.data.definitions
                )
            }
            is MyResult.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = wordDefinition.e.message ?: "Unknown Error",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("") }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            label = { Text(text = "Search Word") },
            leadingIcon = {
                IconButton(onClick = { onSearch(text) }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { text = "" }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Delete Line")
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch(text); focusManager.clearFocus() })
        )
    }
}

@Composable
fun WordDefinitionCard(
    modifier: Modifier = Modifier,
    word: String,
    definitions: List<String>,
) {
    Card(
        modifier = modifier,
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.background,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = word, fontSize = 24.sp)
            Divider()
            definitions.forEachIndexed { id, definition ->
                Row(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = (id + 1).toString() + ". ")
                    Text(text = definition)
                }
            }
        }
    }
}

@Preview
@Composable
fun DictionaryScreenPreview() {
    MyAndroidTheme {
        Surface {
//            DictionaryScreen(viewModel = DictionaryViewModel())
        }
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    MyAndroidTheme {
        Surface {
            SearchBar(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview
@Composable
fun WordDescriptionCardPreview() {
    MyAndroidTheme {
        Surface {
            WordDefinitionCard(
                modifier = Modifier.fillMaxWidth(),
                word = "Hello",
                definitions = listOf("Greeting", "Hi")
            )
        }
    }
}