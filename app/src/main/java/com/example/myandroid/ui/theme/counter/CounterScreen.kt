package com.example.myandroid.ui.theme.counter

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myandroid.ui.theme.MyAndroidTheme

const val COUNTER_LIMIT = 99

@Composable
fun CounterScreen() {
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
            state = listState
        ) {
            repeat(8) {
                item {
                    CounterCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        backgroundColor = MaterialTheme.colors.background,
                        elevation = 10.dp
                    )
                }
            }
        }
    }
}


@Composable
fun CounterCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    elevation: Dp = 1.dp,
    title: String = ""
) {
    Card(
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = border,
        elevation = elevation
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(title, modifier.weight(1f), fontSize = 24.sp)

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Option")
                }
            }
            Counter(modifier = Modifier.padding(16.dp))
        }
    }
}


@Composable
fun Counter(
    modifier: Modifier = Modifier
) {
    var counter by remember {
        mutableStateOf(0)
    }

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
                onClick = { if (counter < COUNTER_LIMIT) counter++ },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colors.primary)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
            IconButton(
                onClick = { if (counter > 0) counter-- },
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
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
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
                Text("Add", fontSize = 20.sp)
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
            CounterScreen()
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
fun CounterRenameDialogPreview() {
    MyAndroidTheme() {
        Surface {
            CounterRenameDialog(title = "Counter 1", onDismiss = {}, onDone = {})
        }
    }
}
