package com.devsphere.pneumoscan.presentation.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.devsphere.pneumoscan.R
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PneumoScan_Splash)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val auth = FirebaseAuth.getInstance()
            val intent = Intent(this, MainActivity::class.java)

            // 🔹 Pass a flag so MainActivity knows where to start
            if (auth.currentUser != null) {
                intent.putExtra("startDestination", "home")
            } else {
                intent.putExtra("startDestination", "login")
            }

            startActivity(intent)
            finish()
        }, 1500)  // 1.5s splash delay
    }
}
