package com.example.thirdtry

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.thirdtry.ui.components.MeetUpTabRow
import com.example.thirdtry.ui.theme.MeetUpTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            getAllMeetUps()
            MeetUpTryApp()
        }
    }
}

@Composable
fun MeetUpTryApp() {
    MeetUpTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = meetUpTabRowScreens.find { it.route == currentDestination?.route } ?: CreateMeetUp
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                MeetUpTabRow(
                    allScreens = meetUpTabRowScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(
                            newScreen.route,
                            pRestoreState = false)
                                    },
                    currentScreen = currentScreen,
                )
            }
        ) { innerPadding ->
            MeetUpNavHost(
                navController = navController,
                scaffoldState = scaffoldState,
                scope = scope,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

fun getAllMeetUps() {
    val db = Firebase.firestore
    val meetUpsCollection = db.collection("Meet Ups")
    var meetUpToBeAdded: MutableMap<String, Any>

    meetUps.clear()

    meetUpsCollection
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                //meetUpToBeAdded is one read, rather than a read for every value for a MeetUp object
                meetUpToBeAdded = document.data as MutableMap<String, Any>
                meetUps.add(
                    object : MeetUp {
                        override val title: String = meetUpToBeAdded["title"].toString()
                        override val date: String = meetUpToBeAdded["date"].toString()
                        override val time: String = meetUpToBeAdded["time"].toString()
                        override val location: String = meetUpToBeAdded["location"].toString()
                        override val extraInfo: String = meetUpToBeAdded["extraInfo"].toString()
                    }
                )
                Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(ContentValues.TAG, "Error getting documents: ", exception)
        }
}
