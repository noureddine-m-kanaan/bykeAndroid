package com.example.afya.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.afya.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private var extras: Bundle? = null
    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        extras = intent.extras

        bottomNavigation = findViewById(R.id.bottom_navigation)
        val navHost = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHost.navController
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tripsFragment -> {
                    navController.navigate(R.id.tripsFragment)
                }
                R.id.userFragment -> {
                    navController.navigate(R.id.userFragment)
                }
            }
            true
        }
    }

    fun getExtra(): Bundle? {
        return extras
    }

    fun logout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.removeExtra("DISPLAY_NAME")
        intent.removeExtra("TOKEN")
        intent.removeExtra("USER_ID")
        startActivity(intent)
    }
}