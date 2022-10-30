package com.example.thirdtry

val events: MutableList<Event> = mutableListOf()

interface Event {
    val id: Int
        get() = events.size
    val title: String
    val date: String
    val time: String
    val location: String
    val extraInfo: String
}