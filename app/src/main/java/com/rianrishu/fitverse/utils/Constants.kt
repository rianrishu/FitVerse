package com.rianrishu.fitverse.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.rianrishu.fitverse.R

enum class ErrorType(val title: Int, val message: Int, val image: Int) {
    NO_INTERNET(R.string.no_internet_title, R.string.no_internet_message, R.drawable.no_internet),
    UNKNOWN(R.string.unknown_error_title, R.string.unknown_error_message, R.drawable.no_internet)
}

enum class NavigationItem(
    val title: String,
    val icon: ImageVector
) {
    Home(
        icon = Icons.Default.Home,
        title = "Home"
    ),
    Profile(
        icon = Icons.Default.Person,
        title = "Profile"
    ),
    Browse(
        icon = Icons.Default.PlayArrow,
        title = "Browse"
    ),
    Settings(
        icon = Icons.Default.Settings,
        title = "Settings"
    )
}

enum class CustomDrawerState {
    Opened,
    Closed
}
