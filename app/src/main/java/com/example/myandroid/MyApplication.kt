package com.example.myandroid

import android.app.Application
import com.example.myandroid.data.AppDatabase

class MyApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
}