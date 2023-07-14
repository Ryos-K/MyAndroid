package com.example.myandroid.ui.dictionary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.myandroid.data.WordInfoRepository

const val dictionaryNavigationRoute = "dictionary"

fun NavController.navigateToDictionary(navOptions: NavOptions? = null) {
    this.navigate(dictionaryNavigationRoute, navOptions)
}

fun NavGraphBuilder.dictionaryScreen(repository: WordInfoRepository, onOpenDrawer: () -> Unit = {}) {
    composable(
        route = dictionaryNavigationRoute
    ) {
        val viewModel = DictionaryViewModel(repository)
        DictionaryScreen(viewModel = viewModel, onOpenDrawer = onOpenDrawer)
    }
}