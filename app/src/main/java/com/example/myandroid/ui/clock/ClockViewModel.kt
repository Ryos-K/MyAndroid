package com.example.myandroid.ui.clock

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Timer
import kotlin.concurrent.timerTask

const val TIMER_INTERVAL = 100
class ClockViewModel: ViewModel() {

    private val _clockState = MutableStateFlow(ClockState(0, false))
    val clockState = _clockState.asStateFlow()

    private val timer = Timer()
    private val timerTask = timerTask {
        _clockState.update { it.copy(timeMillis = it.timeMillis + TIMER_INTERVAL) }
    }

    fun setClock(timeMillis: Long) {
        _clockState.update { it.copy(timeMillis = timeMillis) }
    }

    fun stopClock() {
        _clockState.update { it.copy(isRunning = false) }
        timer.cancel()
    }

    fun resumeClock() {
        _clockState.update { it.copy(isRunning = true) }
        timer.scheduleAtFixedRate(timerTask, 0, TIMER_INTERVAL.toLong())
    }
}