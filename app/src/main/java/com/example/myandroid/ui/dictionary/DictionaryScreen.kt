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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myandroid.ui.theme.MyAndroidTheme

@Composable
fun DictionaryScreen(
    viewModel: DictionaryViewModel
) {
    Column {
        SearchBar(modifier = Modifier.fillMaxWidth())
        WordDescriptionCard(modifier = Modifier.fillMaxWidth().padding(8.dp), word = "aaa", descriptions = listOf("aiueo", "asdfdsaoih"))
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
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
            onValueChange = { text = it; onValueChange(text) },
            label = { Text(text = "Search Word") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                IconButton(onClick = { text = ""; onValueChange(text) }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Delete Line")
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )
    }
}

@Composable
fun WordDescriptionCard(
    modifier: Modifier = Modifier,
    word: String,
    descriptions: List<String>,
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
            descriptions.forEachIndexed {id, description ->
                Row(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = (id + 1).toString() + ". ")
                    Text(text = description)
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
            DictionaryScreen(viewModel = DictionaryViewModel())
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
            WordDescriptionCard(
                modifier = Modifier.fillMaxWidth(),
                word = "Hello",
                descriptions = listOf("Greeting", "Hi")
            )
        }
    }
}