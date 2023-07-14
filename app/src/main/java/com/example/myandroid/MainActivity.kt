package com.example.myandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.example.myandroid.ui.MyApp
import com.example.myandroid.ui.theme.MyAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val database by lazy { (application as MyApplication).database }
        super.onCreate(savedInstanceState)

        setContent {
            MyAndroidTheme {
                Surface {
                    MyApp(database = database)
                }
            }
        }
    }
}