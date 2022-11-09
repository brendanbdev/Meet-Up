package com.example.thirdtry

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.thirdtry.ui.components.HardcoreTabRow
import com.example.thirdtry.ui.theme.ThirdTryTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            getAllEvents(creatingEvent = false, null, null, null)
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
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                HardcoreTabRow(
                    allScreens = hardcoreTabRowScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(
                            newScreen.route,
                            pRestoreState = true)
                                    },
                    currentScreen = currentScreen,
                )
            }
        ) { innerPadding ->
            HardcoreNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                scaffoldState = scaffoldState,
                scope = scope
            )
        }
    }
}

fun getAllEvents(
    creatingEvent: Boolean,
    navController: NavHostController?,
    scope: CoroutineScope?,
    scaffoldState: ScaffoldState?
) {
    val db = Firebase.firestore
    val eventsCollection = db.collection("events")
    var eventToBeAdded: MutableMap<String, Any>

    events.clear()

    eventsCollection
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                //eventToBeAdded is one read, rather than a read for every value for an Event object
                eventToBeAdded = document.data as MutableMap<String, Any>
                events.add(
                    object : Event {
                        override val title: String = eventToBeAdded["title"].toString()
                        override val date: String = eventToBeAdded["date"].toString()
                        override val time: String = eventToBeAdded["time"].toString()
                        override val location: String = eventToBeAdded["location"].toString()
                        override val extraInfo: String = eventToBeAdded["extraInfo"].toString()
                    }
                )
                Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
            }
            if (creatingEvent) {
                navController?.navigateSingleTopTo(
                    EventList.route,
                    pRestoreState = true
                )
                scope?.launch {
                    scaffoldState?.snackbarHostState?.showSnackbar("Your event was created!")
                }
            }
        }
        .addOnFailureListener { exception ->
            Log.d(ContentValues.TAG, "Error getting documents: ", exception)
        }
}



