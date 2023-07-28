package com.example.myandroid.ui.clock

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myandroid.ui.theme.MyAndroidTheme
import kotlin.math.max
import kotlin.time.Duration.Companion.hours

@Composable
fun ClockScreen(
    viewModel: ClockViewModel
) {
    AnalogClock(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        clockState = viewModel.clockState.collectAsState().value,
        secondHandWeight = 0.9f,
        minuteHandWeight = 0.8f,
        hourHandWeight = 0.5f
    )
}

@Composable
fun AnalogClock(
    modifier: Modifier = Modifier,
    clockState: ClockState,
    secondHandWeight: Float,
    minuteHandWeight: Float,
    hourHandWeight: Float,
) {
    // val brush = Brush.sweepGradient(listOf(Color.hsv(0f, 100f, 100f), Color.hsv(180f, 100f, 100f)))
    Canvas(modifier = modifier) {
        val (width, height) = size

    }
}


@Composable
fun DigitalClock(

) {

}

@Composable
@Preview
fun ClockScreenPreview() {
    MyAndroidTheme {
        Surface {
            val viewModel = ClockViewModel().also { it.setClock(1000 * (60 * (60 * 2 + 30) + 55)) }
            ClockScreen(viewModel)
            viewModel.resumeClock()
        }
    }
}