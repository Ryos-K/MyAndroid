package com.example.myandroid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myandroid.database.AppDatabase
import com.example.myandroid.navigation.MyNavHost
import com.example.myandroid.navigation.TopLevelDestination
import com.example.myandroid.navigation.navigateToTopLevelDestination
import com.example.myandroid.ui.counter.counterNavigationRoute
import com.example.myandroid.ui.dictionary.dictionaryNavigationRoute
import kotlinx.coroutines.launch

@Composable
fun MyApp() {

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    val navOptions = NavOptions
        .Builder()
        .setLaunchSingleTop(true)
        .build()

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
        ?: counterNavigationRoute

    val topLevelDestinations = listOf(
        TopLevelDestination.Counter,
        TopLevelDestination.Dictionary,
        TopLevelDestination.Viewer
    )

    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            Text(text = "MyAndroid", fontSize = 32.sp, modifier = Modifier.fillMaxWidth().padding(12.dp))
            Divider(color = MaterialTheme.colors.onBackground)
            topLevelDestinations.forEach { dest ->
                DrawerItem(
                    isSelected = currentDestination == dest.route,
                    icon = dest.icon,
                    title = dest.title,
                    onClick = {
                        navController.navigateToTopLevelDestination(dest, navOptions)
                        scope.launch { drawerState.close() }
                    })
            }
        }) {


        MyNavHost(
            navController = navController,
            startDestination = counterNavigationRoute,
        )
    }
}

@Composable
fun DrawerItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(8.dp)
            .clip(MaterialTheme.shapes.small)
            .background(
                if (isSelected) MaterialTheme.colors.primary else Color.Gray
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(imageVector = icon, contentDescription = title)
        Text(
            modifier = Modifier.fillMaxWidth(0.7f), text = title, color =
            if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
        )
    }
}