package com.example.myandroid.ui.clock


data class ClockState(
    val timeMillis: Long,
    val isRunning: Boolean,
) {
    val seconds: Float
        get() = (timeMillis.toFloat() / 1000 % 60)
    val minutes: Float
        get() = (timeMillis.toFloat() / 1000 / 60 % 60)
    val hours: Float
        get() = (timeMillis.toFloat() / 1000 / 60 / 60 % 12)

    val secondsAngle: Float
        get() = seconds * 6f
    val minutesAngle: Float
        get() = minutes * 6f
    val hoursAngle: Float
        get() = hours * 30f
}