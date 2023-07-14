package com.example.myandroid.ui.counter

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.myandroid.data.CounterRepository

const val counterNavigationRoute = "counter"

fun NavController.navigationToCounter(navOptions: NavOptions? = null) {
    this.navigate(counterNavigationRoute, navOptions)
}

fun NavGraphBuilder.counterScreen(repository: CounterRepository) {
    composable(
        route = counterNavigationRoute,
    ) {
        val viewModel = CounterViewModel(repository)
        CounterScreen(viewModel = viewModel)
    }
}