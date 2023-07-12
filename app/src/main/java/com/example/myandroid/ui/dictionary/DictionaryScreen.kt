package com.example.myandroid.ui.dictionary

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myandroid.model.WordDefinition
import com.example.myandroid.ui.theme.MyAndroidTheme
import com.example.myandroid.utils.MyResult

@Composable
fun DictionaryScreen(
    viewModel: DictionaryViewModel
) {
    val wordDefinitionState = viewModel.searchedState.collectAsState()
    val historyState = viewModel.historyState.collectAsState(initial = listOf())
    var current by remember {
        mutableStateOf(0)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when (current) {
            0 -> Search(
                modifier = Modifier.weight(1f),
                onSearch = { text -> viewModel.loadWordDefinition(text) },
                result = wordDefinitionState.value
            )
            1 -> History(
                modifier = Modifier.weight(1f),
                wordDefinitionList = historyState.value
            )
        }
        BottomBar(
            items = listOf(
                Pair("Search", Icons.Filled.Search),
                Pair("History", Icons.Filled.History)
            ), selectedItem = current,
            onSelect = { index -> current = index }
        )
    }

}

@Composable
fun Search(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    result: MyResult<WordDefinition>,
) {
    Column(modifier = modifier) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            onSearch = onSearch
        )

        when (result) {
            is MyResult.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is MyResult.Success -> {
                LazyColumn() {
                    item {
                        WordDefinitionCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            word = result.data.word, definitions = result.data.definitions
                        )
                    }
                }
            }
            is MyResult.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = result.e.message ?: "Unknown Error",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun History(
    modifier: Modifier = Modifier,
    wordDefinitionList: List<WordDefinition>,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(wordDefinitionList) { item ->
            WordDefinitionCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                word = item.word, definitions = item.definitions
            )
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
fun BottomBar(
    modifier: Modifier = Modifier,
    items: List<Pair<String, ImageVector>>,
    selectedItem: Int,
    onSelect: (Int) -> Unit = {}
) {
    BottomNavigation(modifier = modifier) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                selected = selectedItem == index,
                onClick = { onSelect(index) },
                icon = { Icon(imageVector = item.second, contentDescription = item.first) },
                label = { Text(text = item.first) },
                alwaysShowLabel = false,
            )
        }
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
fun SearchBarPreview() {
    MyAndroidTheme {
        Surface {
            SearchBar(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    MyAndroidTheme {
        Surface {
            var selectedItem by remember {
                mutableStateOf(1)
            }

            BottomBar(
                items = listOf(
                    Pair("Search", Icons.Filled.Search),
                    Pair("History", Icons.Filled.History)
                ),
                selectedItem = selectedItem,
                onSelect = { index -> selectedItem = index }
            )
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