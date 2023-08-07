package com.example.myandroid.ui.calculator

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val calculatorNavigationRoute = "calculator"

fun NavController.navigateToCalculator(navOptions: NavOptions? = null) {
    this.navigate(calculatorNavigationRoute, navOptions)
}

fun NavGraphBuilder.calculatorScreen() {
    composable(
        route = calculatorNavigationRoute
    ) {
        CalculatorScreen()
    }
}