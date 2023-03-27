package com.example.thirdtry

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector

interface MeetUpScreenDestination {
    val icon: ImageVector
    val route: String
}

object CreateMeetUp : MeetUpScreenDestination {
    override val icon = Icons.Filled.Add
    override val route = "Create"
}

object MeetUpList : MeetUpScreenDestination {
    override val icon = Icons.Filled.List
    override val route = "Meet Ups"
}

val meetUpTabRowScreens = listOf(CreateMeetUp, MeetUpList)