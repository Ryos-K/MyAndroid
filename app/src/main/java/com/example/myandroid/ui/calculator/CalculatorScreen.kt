package com.example.myandroid.ui.calculator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CalculatorScreen(
    calculatorViewModel : CalculatorViewModel = hiltViewModel()
) {
    val expr = "2 - 1 + 3"
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = calculatorViewModel.getMessage("Hello World!"),
            )
            Text(
                text = "$expr = ${calculatorViewModel.calculate(expr)}"
            )
        }
    }
}