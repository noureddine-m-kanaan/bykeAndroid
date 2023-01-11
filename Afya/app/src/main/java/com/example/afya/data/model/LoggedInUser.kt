package com.example.afya.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val nomUtil: String,
    val token: String
)