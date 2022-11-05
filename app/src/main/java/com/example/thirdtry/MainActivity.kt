package com.example.thirdtry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
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
            HardcoreNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThirdTryAppPreview() {
    ThirdTryApp()
}


