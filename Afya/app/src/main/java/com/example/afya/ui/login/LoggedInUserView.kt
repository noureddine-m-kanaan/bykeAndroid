package com.example.afya.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val numUtil: Int,
    val token: String
)