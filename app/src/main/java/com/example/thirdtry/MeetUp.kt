package com.example.thirdtry

val meetUps: MutableList<MeetUp> = mutableListOf()

interface MeetUp {
    val title: String
    val date: String
    val time: String
    val location: String
    val extraInfo: String
}