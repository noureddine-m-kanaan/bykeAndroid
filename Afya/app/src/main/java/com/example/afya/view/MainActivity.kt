package com.example.afya.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.afya.R

class MainActivity : AppCompatActivity() {
    private var extras: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extras = intent.extras
        setContentView(R.layout.activity_main)
    }

    fun getExtra(): Bundle? {
        return extras
    }
}