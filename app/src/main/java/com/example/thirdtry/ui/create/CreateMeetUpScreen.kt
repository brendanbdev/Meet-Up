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
fun MeetUpCreationScreen(
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
    val meetUp = hashMapOf(
        "title" to titleTextFieldState,
        "date" to dateTextFieldState,
        "time" to timeTextFieldState,
        "location" to locationTextFieldState,
        "extraInfo" to extraInfoTextFieldState,
    )
    val db = Firebase.firestore
    val meetUpsCollection = db.collection("Meet Ups")

    Column(
        modifier
            .padding(start = 32.dp, end = 32.dp, top = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Post a Meet Up:",
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground,
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
                    message = "Creating your Meet Up...",
                    duration = SnackbarDuration.Indefinite
                )
            }
                meetUpsCollection
                    .add(meetUp)
                    .addOnSuccessListener { documentReference ->
                        Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        var meetUpToBeAdded: MutableMap<String, Any>

                        meetUps.clear()

                        meetUpsCollection
                            .get()
                            .addOnSuccessListener { result ->
                                for (document in result) {
                                    //meetUpToBeAdded is one read, rather than a read for every value for an MeetUp object
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
                                    titleTextFieldState = ""
                                    dateTextFieldState = ""
                                    timeTextFieldState = ""
                                    locationTextFieldState = ""
                                    extraInfoTextFieldState = ""
                                    navController.navigateSingleTopTo(
                                        MeetUpList.route
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
                            scaffoldState.snackbarHostState.showSnackbar("Your Meet Up was created!")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error adding document", e)
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("There was a problem. Your Meet Up was not created.")
                            buttonEnabled = true
                        }
                    }
            }
        )
        {
            Text("Create Meet Up")
        }
    }
}
