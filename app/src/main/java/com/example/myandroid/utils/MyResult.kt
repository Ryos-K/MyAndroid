package com.example.myandroid.utils

sealed class MyResult<T>() {
    class Loading<T> : MyResult<T>()
    data class Success<T>(val data: T) : MyResult<T>()
    class Error<T>(val e: Throwable) : MyResult<T>()
}
