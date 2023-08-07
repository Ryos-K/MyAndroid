package com.example.myandroid.ui.calculator

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class CalculatorViewModel @Inject constructor() : ViewModel() {

    external fun getMessage(jName: String): String

    external fun calculate(jExpr: String): Int
}