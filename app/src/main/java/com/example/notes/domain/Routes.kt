package com.example.notes.domain

sealed class Routes(val route: String) {
    object Notes : Routes("Notes")
    object Details: Routes("Details")
    object Settings : Routes("Settings")
    object About: Routes("About")
}