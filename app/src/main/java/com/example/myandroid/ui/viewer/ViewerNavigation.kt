package com.example.myandroid.ui.viewer

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val viewerNavigationRoute = "viewer"

fun NavController.navigateToViewer(navOptions: NavOptions? = null) {
    this.navigate(viewerNavigationRoute, navOptions)
}

fun NavGraphBuilder.viewerScreen() {
    composable(
        route = viewerNavigationRoute
    ) {
        ViewerScreen()
    }
}