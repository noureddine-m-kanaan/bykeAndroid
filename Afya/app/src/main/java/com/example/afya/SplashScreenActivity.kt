package com.example.afya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.afya.ui.login.LoginActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Keep the splash screen visible for this Activity
        splashScreen.setKeepOnScreenCondition { true } // Il faut ajouter une condition lors de chargemnet des données
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()



    }

}