package com.example.myandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.example.myandroid.ui.MyApp
import com.example.myandroid.ui.theme.MyAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAndroidTheme {
                Surface {
                    MyApp()
                }
            }
        }
    }
}