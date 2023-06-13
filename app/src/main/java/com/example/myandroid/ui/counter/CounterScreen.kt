package com.example.myandroid.ui.counter

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewModelScope
import com.example.myandroid.ui.theme.MyAndroidTheme
import kotlinx.coroutines.launch


@Composable
fun CounterScreen(
    viewModel: CounterViewModel
) {
    val state = viewModel.filteredState.collectAsState(CounterState.Loading).value
    when (state) {
        is CounterState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is CounterState.Success -> {

            CounterSuccessScreen(
                counterCardList = state.counterCards,
                onDelete = { id -> viewModel.deleteCounterCard(id) },
                onClickPlus = { id -> viewModel.incrementCounter(id) },
                onClickMinus = { id -> viewModel.decrementCounter(id) },
                onDoneDialog = { text -> viewModel.insertCounterCard(text) },
                onSearch = { text -> viewModel.setFilterText(text) }
            )
        }
        is CounterState.Error -> {

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CounterSuccessScreen(
    counterCardList: List<CounterUiState>,
    onDelete: (Int) -> Unit = {},
    onClickPlus: (Int) -> Unit = {},
    onClickMinus: (Int) -> Unit = {},
    onDoneDialog: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
) {
    val listState = rememberLazyListState()
    val isExpanded by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }
    var showDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            CounterBottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onSearch
            )
        },
        floatingActionButton = {
            CounterFAB(
                modifier = Modifier.wrapContentSize(),
                onClick = { showDialog = true },
                isExpanded = isExpanded
            )
        }
    ) {
        if (showDialog) {
            CounterDialog(onDismiss = { showDialog = false }, onDone = { text ->
                onDoneDialog(text)
                showDialog = false
            })
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            state = listState,
        ) {
            items(counterCardList, key = { card -> card.id }) { card ->
                CounterCard(
                    modifier = Modifier
                        .animateItemPlacement()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    uiState = card,
                    onDelete = { onDelete(card.id) },
                    onClickPlus = { onClickPlus(card.id) },
                    onClickMinus = { onClickMinus(card.id) }
                )
            }
        }
    }
}

@Composable
fun CounterBottomAppBar(
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
            value = text,
            onValueChange = {
                text = it
                onValueChange(it)
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            label = { Text("Search Title") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    modifier = Modifier.padding(16.dp)
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    text = ""
                    onValueChange("")
                }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Delete Line")
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )
    }
}

@Composable
fun CounterCard(
    modifier: Modifier = Modifier,
    uiState: CounterUiState,
    onDelete: () -> Unit,
    onClickPlus: () -> Unit,
    onClickMinus: () -> Unit,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
) {
    Card(
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = 8.dp
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(uiState.title, Modifier.weight(1f), fontSize = 24.sp)
                IconButton(onClick = { onDelete() }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Option")
                }
            }
            Counter(
                modifier = Modifier.padding(16.dp, 4.dp, 16.dp, 16.dp),
                counter = uiState.counter,
                onClickPlus = onClickPlus,
                onClickMinus = onClickMinus
            )
        }
    }
}


@Composable
fun Counter(
    modifier: Modifier = Modifier,
    counter: Int,
    onClickPlus: () -> Unit,
    onClickMinus: () -> Unit,
) {
    Column(modifier = modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                counter.toString(),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = { onClickPlus() },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colors.primary)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(
                onClick = { onClickMinus() },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colors.primary)
            ) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = "Remove",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun CounterFAB(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isExpanded: Boolean = true,
    shape: Shape = RoundedCornerShape(16.dp),
    backgroundColor: Color = MaterialTheme.colors.secondary,
    contentColor: Color = contentColorFor(backgroundColor),
) {
    FloatingActionButton(
        onClick = { onClick() },
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .animateContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Counter")
            if (isExpanded) {
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Counter", fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun CounterDialog(onDismiss: () -> Unit, onDone: (String) -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        var text by remember { mutableStateOf("") }
        Card {
            Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Add Counter", fontSize = 30.sp)
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("New Counter Title") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        onDone(text)
                        text = ""
                    })
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    onDone(text)
                    text = ""
                }) {
                    Text("Done")
                }
            }
        }
    }
}


//@Composable
//@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
//@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
//fun CounterPreview() {
//    MyAndroidTheme {
//        Surface() {
//            val counterViewModel by remember { mutableStateOf(CounterViewModel()) }
//            CounterScreen(counterViewModel)
//        }
//    }
//}

@Composable
@Preview
fun CounterFABPreview() {
    MyAndroidTheme() {
        Surface {
            CounterFAB(onClick = { /*TODO*/ })
        }
    }
}

@Composable
@Preview
fun CounterSuccessScreenPreview() {
    MyAndroidTheme {
        Surface {
            val list = listOf(
                CounterUiState(1, "Counter 1", 0),
                CounterUiState(2, "Counter 2", 2),
                CounterUiState(3, "Counter 3", 4),
                CounterUiState(4, "Counter 4", 10),
                CounterUiState(5, "Counter 5", 40),
                CounterUiState(6, "Counter 6", 50),
                CounterUiState(7, "Counter 7", 60),
                CounterUiState(8, "Counter 8", 70),
            )
            CounterSuccessScreen(
                counterCardList = list,
                onDelete = {},
                onClickPlus = {},
                onClickMinus = {},
                onDoneDialog = {}
            )
        }
    }
}

@Composable
@Preview
fun CounterDialogPreview() {
    MyAndroidTheme() {
        Surface {
            CounterDialog(onDismiss = {}, onDone = {})
        }
    }
}

@Composable
@Preview
fun CounterBottomAppBarPreview() {
    MyAndroidTheme {
        Surface {
            CounterBottomAppBar(
                modifier = Modifier.background(MaterialTheme.colors.primary),
                onValueChange = {}
            )
        }
    }
}
