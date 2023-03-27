package com.example.thirdtry.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thirdtry.MeetUp
import com.example.thirdtry.meetUps

@Composable
fun MeetUpListScreen() {
    MeetUpLazyList()
}

@Composable
private fun MeetUpCard(meetUp: MeetUp) {
    Surface(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = meetUp.title, style = MaterialTheme.typography.h1)
            Spacer(Modifier.height(16.dp))
            Text(text = "- " + meetUp.date + ", " + meetUp.time)
            Text(text = "- " + meetUp.location)
            Text(text = "- " + meetUp.extraInfo)
        }
    }
}

@Composable
fun MeetUpLazyList() {
    val meetUpsListRemembered by rememberSaveable {
        mutableStateOf(meetUps.toList())
    }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp)) {
        items(items = meetUpsListRemembered) { meetUp ->
            MeetUpCard(meetUp = meetUp)
        }
    }
}
