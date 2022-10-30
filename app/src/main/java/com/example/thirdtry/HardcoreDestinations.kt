package com.example.thirdtry

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector

interface HardcoreDestination {
    val icon: ImageVector
    val route: String
}

object CreateEvent : HardcoreDestination {
    override val icon = Icons.Filled.Add
    override val route = "create"
}

object EventList : HardcoreDestination {
    override val icon = Icons.Filled.List
    override val route = "list"
}

val hardcoreTabRowScreens = listOf(CreateEvent, EventList)