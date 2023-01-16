package com.example.afya.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope

import com.example.afya.R
import com.example.afya.api.API
import com.example.afya.api.ApiAdress
import com.example.afya.data.model.RegisterRequest
import com.example.afya.ui.register.RegisterFormState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterViewModel() : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult


    fun register(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val req = Retrofit.Builder()
                    .baseUrl(ApiAdress.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(API::class.java)
                    .register(RegisterRequest(username, password, "visiteur"))
                _registerResult.postValue(req.isSuccessful)
            } catch (e: Exception) {
                _registerResult.postValue(false)
            }
        }
    }

    fun registerDataChanged(username: String, password: String, passwordConfirm: String) {
        if (!isUserNameValid(username)) {
            _registerForm.postValue(RegisterFormState(usernameError = R.string.invalid_username))
        } else if (!isPasswordValid(password)) {
            _registerForm.postValue(RegisterFormState(passwordError = R.string.invalid_password))
        } else if (!isPasswordConfirmValid(password, passwordConfirm)) {
            _registerForm.postValue(RegisterFormState(passwordConfirmError = R.string.invalid_password_confirm))
        } else {
            _registerForm.postValue(RegisterFormState(isDataValid = true))
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

    private fun isPasswordConfirmValid(password: String, passwordConfirm: String): Boolean {
        return password == passwordConfirm
    }
}