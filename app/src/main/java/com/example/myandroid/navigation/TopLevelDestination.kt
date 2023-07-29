package com.example.myandroid.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.myandroid.ui.counter.counterNavigationRoute
import com.example.myandroid.ui.counter.navigationToCounter
import com.example.myandroid.ui.dictionary.dictionaryNavigationRoute
import com.example.myandroid.ui.dictionary.navigateToDictionary
import com.example.myandroid.ui.viewer.navigateToViewer
import com.example.myandroid.ui.viewer.viewerNavigationRoute

enum class TopLevelDestination(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    Counter(
        route = counterNavigationRoute,
        title = "Counter",
        icon = Icons.Filled.PlusOne
    ),
    Dictionary(
        route = dictionaryNavigationRoute,
        title = "Dictionary",
        icon = Icons.Filled.EditNote
    ),
    Viewer(
        route = viewerNavigationRoute,
        title = "Viewer",
        icon = Icons.Filled.Image
    )
}

fun NavController.navigateToTopLevelDestination(destination: TopLevelDestination, navOptions: NavOptions? = null) {
    when (destination) {
        TopLevelDestination.Counter -> this.navigationToCounter(navOptions)
        TopLevelDestination.Dictionary -> this.navigateToDictionary(navOptions)
        TopLevelDestination.Viewer -> this.navigateToViewer(navOptions)
    }
}