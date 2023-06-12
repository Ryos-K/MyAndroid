package com.example.myandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.example.myandroid.ui.theme.MyAndroidTheme
import com.example.myandroid.ui.counter.CounterScreen
import com.example.myandroid.ui.counter.CounterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAndroidTheme {
                Surface {
                    val counterViewModel = CounterViewModel()
                    CounterScreen(counterViewModel)
                }
            }
        }
    }
}