package com.example.afya.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.afya.R
import com.example.afya.databinding.ActivityRegisterBinding
import com.example.afya.viewmodel.RegisterViewModel
import com.example.afya.viewmodel.RegisterViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var tvLoginLink: TextView
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.registerUsername
        val password = binding.registerPassword
        val passwordConfirm = binding.registerConfirmPassword
        val register = binding.registerButton

        registerViewModel = ViewModelProvider(this, RegisterViewModelFactory())[(RegisterViewModel::class.java)]

        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            register.isEnabled = registerState.isDataValid

            if (registerState.usernameError != null) {
                username.error = getString(registerState.usernameError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)
            }
            if (registerState.passwordConfirmError != null) {
                passwordConfirm.error = getString(registerState.passwordConfirmError)
            }
        })

        registerViewModel.registerResult.observe(this@RegisterActivity, Observer {
            val registerResult = it ?: return@Observer

            if(registerResult) {
                showRegisterSuccess()
            }
        })

        username.afterTextChanged {
            registerViewModel.registerDataChanged(
                username.text.toString(),
                password.text.toString(),
                passwordConfirm.text.toString()
            )
        }

        password.afterTextChanged {
            registerViewModel.registerDataChanged(
                username.text.toString(),
                password.text.toString(),
                passwordConfirm.text.toString()
            )
        }

        passwordConfirm.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    username.text.toString(),
                    password.text.toString(),
                    passwordConfirm.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        registerViewModel.register(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            register.setOnClickListener {
                registerViewModel.register(
                    username.text.toString(),
                    password.text.toString()
                )
            }
        }

        tvLoginLink = findViewById(R.id.register_tv_loginLink)
        tvLoginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showRegisterSuccess(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}