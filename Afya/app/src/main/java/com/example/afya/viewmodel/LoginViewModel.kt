package com.example.afya.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.afya.data.LoginRepository
import com.example.afya.data.Result

import com.example.afya.R
import com.example.afya.ui.login.LoggedInUserView
import com.example.afya.ui.login.LoginFormState
import com.example.afya.ui.login.LoginResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginRepository.login(username, password)
            if (result is Result.Success) {
                _loginResult.postValue(LoginResult(success = LoggedInUserView(displayName = result.data.nomUtil, numUtil = result.data.numUtil, token = result.data.token)))
            } else {
                _loginResult.postValue(LoginResult(error = result.toString().substringAfter("IOException:").dropLast(1))
                )
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.postValue(LoginFormState(usernameError = R.string.invalid_username))
        } else if (!isPasswordValid(password)) {
            _loginForm.postValue(LoginFormState(passwordError = R.string.invalid_password))
        } else {
            _loginForm.postValue(LoginFormState(isDataValid = true))
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}