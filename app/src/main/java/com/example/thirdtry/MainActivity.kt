package com.example.thirdtry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import com.example.thirdtry.ui.components.HardcoreTabRow
import com.example.thirdtry.ui.theme.ThirdTryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThirdTryApp()
        }
    }
}

@Composable
fun ThirdTryApp() {
    ThirdTryTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = hardcoreTabRowScreens.find { it.route == currentDestination?.route } ?: CreateEvent
        Scaffold(
            topBar = {
                HardcoreTabRow(
                    allScreens = hardcoreTabRowScreens,
                    // Pass the callback like this,
                    // defining the navigation action when a tab is selected:
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                                    },
                    currentScreen = currentScreen,
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = CreateEvent.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                // builder parameter will be defined here as the graph
                composable(route = CreateEvent.route) {
                    EventCreationScreen()
                }
                composable(route = EventList.route) {
                    EventListScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThirdTryAppPreview() {
    ThirdTryApp()
}

//@Preview(showBackground = true)
//@Composable
//fun ThirdTryAppPreview() {
//    ThirdTryTheme {
//        var currentScreen: HardcoreDestination by remember { mutableStateOf(CreateEvent) }
//        val navController = rememberNavController()
//        Scaffold() { innerPadding ->
//            NavHost(
//                navController = navController,
//                startDestination = CreateEvent.route,
//                modifier = Modifier.padding(innerPadding)
//            ) {
//                composable(route = CreateEvent.route) {
//                    CreateEvent.screen()
//                }
//                composable(route = CreateEvent.route) {
//                    EventList.screen()
//                }
//            }
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun ThirdTryAppPreviewAlpha() {
    ThirdTryTheme {
        var currentScreen: HardcoreDestination by remember { mutableStateOf(CreateEvent) }
        val navController = rememberNavController()
        Scaffold() { padding ->
            EventCreationScreen(Modifier.padding(padding))
        }
    }
}

@Composable
fun EventCreationScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .padding(start = 32.dp, end = 32.dp, top = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = "Post an event:",
            style = MaterialTheme.typography.h1,
            modifier = Modifier
        )
        var titleTextFieldState by remember {
            mutableStateOf("")
        }
        var dateTextFieldState by remember {
            mutableStateOf("")
        }
        var timeTextFieldState by remember {
            mutableStateOf("")
        }
        var locationTextFieldState by remember {
            mutableStateOf("")
        }
        var extraInfoTextFieldState by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.title_label))
            },
            value = titleTextFieldState,
            onValueChange = {
                titleTextFieldState = it
            },
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.date_label))
            },
            value = dateTextFieldState,
            onValueChange = {
                dateTextFieldState = it
            },
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.time_label))
            },
            value = timeTextFieldState,
            onValueChange = {
                timeTextFieldState = it
            },
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.location_label))
            },
            value = locationTextFieldState,
            onValueChange = {
                locationTextFieldState = it
            },
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.extra_info_label))
            },
            value = extraInfoTextFieldState,
            onValueChange = {
                extraInfoTextFieldState = it
            },
        )
        Button(onClick = { /*TODO*/ }) {
            Text("Create Event")
        }
    }
}

@Composable
fun EventListScreen(modifier: Modifier = Modifier) {
    Text(text = "Hello, Brendan.")
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
