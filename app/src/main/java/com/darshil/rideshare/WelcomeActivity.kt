package com.darshil.rideshare

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.darshil.rideshare.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide action bar
        supportActionBar?.hide()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnSignIn.setOnClickListener {
            // TODO: Navigate to SignInActivity (we'll create this next)
//            Toast.makeText(this, "Sign In clicked - Coming in Day 3!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
            // TODO: Navigate to SignUpActivity
            Toast.makeText(this, "Sign Up clicked - Coming in Day 3!", Toast.LENGTH_SHORT).show()
        }
    }
}