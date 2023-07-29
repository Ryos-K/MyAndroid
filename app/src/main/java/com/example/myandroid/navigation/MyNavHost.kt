package com.example.myandroid.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.myandroid.data.CounterRepository
import com.example.myandroid.data.WordInfoRepository
import com.example.myandroid.database.AppDatabase
import com.example.myandroid.network.DictionaryService
import com.example.myandroid.ui.counter.counterScreen
import com.example.myandroid.ui.dictionary.dictionaryScreen
import com.example.myandroid.ui.viewer.viewerScreen

@Composable
fun MyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        counterScreen()
        dictionaryScreen()
        viewerScreen()
    }
}