package com.example.afya.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val token: String? = null,
    val error: String? = null
)