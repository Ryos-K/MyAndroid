package com.example.myandroid.ui.calculator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CalculatorScreen(
    calculatorViewModel: CalculatorViewModel = hiltViewModel()
) {
    var input by remember { mutableStateOf("") }
    val expr = "2 - 1 + 3"
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = input)
            TextField(
                value = input,
                onValueChange = { input = it },
                visualTransformation = object : VisualTransformation {
                    override fun filter(text: AnnotatedString): TransformedText {
                        val originalToTransformed = mutableListOf<Int>()
                        val transformedToOriginal = mutableListOf<Int>()

                        return TransformedText(
                            text = AnnotatedString(text = buildString {
                                text.forEachIndexed { index, c ->
                                    val new = "${c};"

                                    originalToTransformed.add(if (index == text.lastIndex) length else length + 2)
                                    transformedToOriginal.addAll(Array(new.length) { index })

                                    append(new)
                                }
                                originalToTransformed.add(length)
                                transformedToOriginal.add(text.length)
                            }),
                            offsetMapping = object : OffsetMapping {
                                override fun originalToTransformed(offset: Int) =
                                    originalToTransformed[offset]

                                override fun transformedToOriginal(offset: Int) =
                                    transformedToOriginal[offset]
                            }
                        )
                    }
                }
            )

            Text(
                text = calculatorViewModel.getMessage("Hello World!"),
            )
            Text(
                text = "$expr = ${calculatorViewModel.calculate(expr)}"
            )
        }
    }
}

