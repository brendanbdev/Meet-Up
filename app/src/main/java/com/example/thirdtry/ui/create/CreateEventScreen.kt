package com.example.thirdtry.ui.create

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
import com.example.thirdtry.Event
import com.example.thirdtry.R
import com.example.thirdtry.events

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
        Button(onClick = {
            events.add(
                object : Event {
                override val id: Int
                    get() = super.id
                override val title: String
                    get() = titleTextFieldState
                override val date: String
                    get() = dateTextFieldState
                override val time: String
                    get() = timeTextFieldState
                override val location: String
                    get() = locationTextFieldState
                override val extraInfo: String
                    get() = extraInfoTextFieldState
                }
            )
        })
        {
            Text("Create Event")
        }
    }
}
