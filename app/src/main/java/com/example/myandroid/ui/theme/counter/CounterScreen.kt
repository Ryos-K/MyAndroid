package com.example.myandroid.ui.theme.counter

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myandroid.ui.theme.MyAndroidTheme

const val COUNTER_LIMIT = 99

@Composable
fun CounterScreen() {
    Counter(modifier = Modifier.fillMaxWidth())
}

@Composable
fun Counter(
    modifier: Modifier
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
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
fun CounterPreview() {
    MyAndroidTheme {
        Surface() {
            CounterScreen()
        }
    }
}
