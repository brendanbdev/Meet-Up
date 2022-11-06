package com.example.thirdtry.ui.create

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.thirdtry.*
import com.example.thirdtry.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun EventCreationScreen(modifier: Modifier = Modifier) {
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
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = "Post an event:",
            style = MaterialTheme.typography.h1,
            modifier = Modifier
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
        Button(onClick = {
            eventsCollection
                .add(event)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    getAllEvents()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        })
        {
            Text("Create Event")
        }
    }
}
