package com.example.afya.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.afya.MapsActivity
import com.example.afya.R
import com.example.afya.databinding.ActivityLoginBinding
import com.example.afya.ui.login.LoggedInUserView
import com.example.afya.viewmodel.LoginViewModel
import com.example.afya.viewmodel.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.loginUsername
        val password = binding.loginPassword
        val login = binding.loginButton
        val loading = binding.loading

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())[(LoginViewModel::class.java)]

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login?.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username?.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password?.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
        })

        username?.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password?.text.toString()
            )
        }

        password?.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username?.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username?.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login?.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username?.text.toString(), password.text.toString())
            }
        }

        binding.loginTvRegisterLink?.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("DISPLAY_NAME", model.displayName)
        intent.putExtra("USER_ID", model.numUtil)
        intent.putExtra("TOKEN", "Bearer " + model.token)
        startActivity(intent)
        finish()
    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}