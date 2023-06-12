package com.example.myandroid.ui.theme.counter

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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myandroid.ui.theme.MyAndroidTheme


@Composable
fun CounterScreen(
    viewModel: CounterViewModel
) {
    val state = viewModel.counterState.collectAsState().value
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
            val counterCardList = state.counterCards
            CounterSuccessScreen(
                counterCardList = counterCardList,
                onDelete = { id -> viewModel.deleteCounterCard(id) },
                onClickPlus = { id -> viewModel.incrementCounter(id) },
                onClickMinus = { id -> viewModel.decrementCounter(id) }
            )
        }
        is CounterState.Error -> {

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CounterSuccessScreen(
    counterCardList: List<CounterCardUiState>,
    onDelete: (Int) -> Unit,
    onClickPlus: (Int) -> Unit,
    onClickMinus: (Int) -> Unit
) {
    val listState = rememberLazyListState()
    val isExpanded by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    Scaffold(
        floatingActionButton = {
            CounterFAB(
                modifier = Modifier.wrapContentSize(),
                isExpanded = isExpanded
            )
        }
    ) {
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
fun CounterCard(
    modifier: Modifier = Modifier,
    uiState: CounterCardUiState,
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
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
            IconButton(
                onClick = { onClickMinus() },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colors.primary)
            ) {
                Icon(imageVector = Icons.Filled.Remove, contentDescription = "Remove")
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
fun CounterRenameDialog(title: String, onDismiss: () -> Unit, onDone: (String) -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        var value by remember { mutableStateOf("") }
        Card() {
            Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Rename -> '$title'", fontSize = 30.sp)
                TextField(value = value, onValueChange = { value = it })
                Button(onClick = { onDone(value) }) {
                    Text("Done")
                }
            }
        }
    }
}


@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
fun CounterPreview() {
    MyAndroidTheme {
        Surface() {
            val counterViewModel by remember { mutableStateOf(CounterViewModel()) }
            CounterScreen(counterViewModel)
        }
    }
}

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
                CounterCardUiState(1, "Counter 1", 0),
                CounterCardUiState(2, "Counter 2", 2),
                CounterCardUiState(3, "Counter 3", 4),
                CounterCardUiState(4, "Counter 4", 10),
                CounterCardUiState(5, "Counter 5", 40),
                CounterCardUiState(6, "Counter 6", 50),
                CounterCardUiState(7, "Counter 7", 60),
                CounterCardUiState(8, "Counter 8", 70),
            )
            CounterSuccessScreen(
                counterCardList = list,
                onDelete = {},
                onClickPlus = {},
                onClickMinus = {})
        }
    }
}

@Composable
@Preview
fun CounterRenameDialogPreview() {
    MyAndroidTheme() {
        Surface {
            CounterRenameDialog(title = "Counter 1", onDismiss = {}, onDone = {})
        }
    }
}
