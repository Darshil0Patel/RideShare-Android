package com.darshil.rideshare

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.darshil.rideshare.databinding.ActivitySplashBinding
import com.darshil.rideshare.utils.PreferencesManager

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var prefsManager: PreferencesManager
    private val splashTimeOut: Long = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        prefsManager = PreferencesManager(this)

        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNextScreen()
        }, splashTimeOut)
    }

    private fun navigateToNextScreen() {
        val intent = when {
            // User is logged in → Go to MainActivity
            prefsManager.isLoggedIn -> {
                Intent(this, MainActivity::class.java)
            }
            // User hasn't seen onboarding → Show onboarding
//            !prefsManager.hasSeenOnboarding -> {
//                Intent(this, OnboardingActivity::class.java)
//            }
            // Default → Show welcome screen
            else -> {
                Intent(this, WelcomeActivity::class.java)
            }
        }

        startActivity(intent)
        finish()
    }
}