package com.example.thirdtry.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thirdtry.Event
import com.example.thirdtry.events

fun getList(): List<Event> {
    return events.toList()
}

@Composable
fun EventListScreen(modifier: Modifier = Modifier) {
    EventLazyList()
}

@Composable
private fun EventCard(event: Event) {
    Surface() {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = event.title)
            Text(text = event.date)
            Text(text = event.time)
            Text(text = event.location)
            Text(text = event.extraInfo)
        }
    }
}

@Composable
fun EventLazyList() {
    val eventsListRemembered by rememberSaveable {
        mutableStateOf(events.toList())
    }

    LazyColumn() {
        items(items = eventsListRemembered) { event ->
            EventCard(event = event)
        }
    }
}
