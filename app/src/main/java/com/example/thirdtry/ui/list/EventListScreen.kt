package com.example.thirdtry.ui.list

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thirdtry.Event
import com.example.thirdtry.events
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun EventListScreen(modifier: Modifier = Modifier) {
    val db = Firebase.firestore
    val eventsCollection = db.collection("events")
    eventsCollection
        .get()
        .addOnSuccessListener { result ->
        for (document in result) {
            events.add(
                object : Event {
                    override val title: String
                        get() = document.data["title"] as String
                    override val date: String
                        get() = document.data["date"] as String
                    override val time: String
                        get() = document.data["time"] as String
                    override val location: String
                        get() = document.data["location"] as String
                    override val extraInfo: String
                        get() = document.data["extraInfo"] as String
                }
            )
            Log.d(TAG, "${document.id} => ${document.data}")
        }
    }
        .addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }
//    var eventName: String = documentSnapshot
    LazyColumn() {
        items(items = events) { event ->
            EventCard(event = event)
        }
    }
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