package com.example.thirdtry

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.thirdtry.ui.create.MeetUpCreationScreen
import com.example.thirdtry.ui.list.MeetUpListScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun MeetUpNavHost(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = CreateMeetUp.route,
        modifier = modifier
    ) {
        // builder parameter will be defined here as the graph
        composable(route = CreateMeetUp.route) {
            MeetUpCreationScreen(
                scaffoldState = scaffoldState,
                scope = scope,
                navController = navController
            )
        }
        composable(route = MeetUpList.route) {
            MeetUpListScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(
    route: String,
    pRestoreState: Boolean = false
) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // re-selecting the same item
        launchSingleTop = true
        // Restore state when re-selecting a previously selected item
        restoreState = pRestoreState
    }
