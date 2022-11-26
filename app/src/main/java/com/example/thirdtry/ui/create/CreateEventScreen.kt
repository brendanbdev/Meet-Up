package com.example.thirdtry.ui.create

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.thirdtry.*
import com.example.thirdtry.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EventCreationScreen(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    navController: NavHostController
) {
    var titleTextFieldState by rememberSaveable {
        mutableStateOf("")
    }
    var dateTextFieldState by rememberSaveable {
        mutableStateOf("")
    }
    var timeTextFieldState by rememberSaveable {
        mutableStateOf("")
    }
    var locationTextFieldState by rememberSaveable {
        mutableStateOf("")
    }
    var extraInfoTextFieldState by rememberSaveable {
        mutableStateOf("")
    }
    val event = hashMapOf(
        "title" to titleTextFieldState,
        "date" to dateTextFieldState,
        "time" to timeTextFieldState,
        "location" to locationTextFieldState,
        "extraInfo" to extraInfoTextFieldState,
    )
    val db = Firebase.firestore
    val eventsCollection = db.collection("events")

    Column(
        modifier
            .padding(start = 32.dp, end = 32.dp, top = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Post an event:",
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(vertical = 16.dp)
        )
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
        var buttonEnabled by remember {mutableStateOf(true)}
        Button(
            modifier = Modifier.padding(vertical = 16.dp),
            enabled = buttonEnabled,
            onClick = {
                buttonEnabled = false
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Creating your event...",
                    duration = SnackbarDuration.Indefinite
                )
            }
                eventsCollection
                    .add(event)
                    .addOnSuccessListener { documentReference ->
                        Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
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
                                    titleTextFieldState = ""
                                    dateTextFieldState = ""
                                    timeTextFieldState = ""
                                    locationTextFieldState = ""
                                    extraInfoTextFieldState = ""
                                    navController.navigateSingleTopTo(
                                        EventList.route
                                    )
                                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
                                buttonEnabled = true
                            }
                        scope.launch {
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                            scaffoldState.snackbarHostState.showSnackbar("Your event was created!")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error adding document", e)
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("There was a problem. Your event was not created.")
                            buttonEnabled = true
                        }
                    }
            }
        )
        {
            Text("Create Event")
        }
    }
}
