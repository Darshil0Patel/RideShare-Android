package com.darshil.rideshare

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.darshil.rideshare.databinding.ActivitySignInBinding
import com.darshil.rideshare.utils.PreferencesManager
import com.darshil.rideshare.utils.ValidationHelper
import com.darshil.rideshare.utils.ValidationResult

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var prefsManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        prefsManager = PreferencesManager(this)

        // Load saved email if "Remember Me" was checked
        loadSavedCredentials()

        setupClickListeners()
    }

    private fun loadSavedCredentials() {
        if (prefsManager.rememberMe) {
            binding.etEmail.setText(prefsManager.userEmail)
            binding.cbRememberMe.isChecked = true
        }
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            if (validateInputs()) {
                performLogin()
            }
        }

        binding.btnForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInputs(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        // Reset errors
        binding.tilEmail.error = null
        binding.tilPassword.error = null

        // Validate email
        when (val result = ValidationHelper.validateEmail(email)) {
            is ValidationResult.Error -> {
                binding.tilEmail.error = result.message
                binding.etEmail.requestFocus()
                return false
            }
            is ValidationResult.Success -> {}
        }

        // Validate password
        when (val result = ValidationHelper.validatePassword(password)) {
            is ValidationResult.Error -> {
                binding.tilPassword.error = result.message
                binding.etPassword.requestFocus()
                return false
            }
            is ValidationResult.Success -> {}
        }

        return true
    }

    private fun performLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        setLoading(true)

        // TODO: Implement actual authentication (Day 13 - Firebase)
        binding.root.postDelayed({
            setLoading(false)

            // Simulate successful login
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

            // Save user session
            prefsManager.saveUserSession(
                userId = "user123",
                name = "John Doe",
                email = email,
                phone = "+1234567890"
            )

            // Save "Remember Me" preference
            prefsManager.rememberMe = binding.cbRememberMe.isChecked

            // Navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        }, 2000)
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnLogin.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
            binding.btnLogin.isEnabled = false
            binding.etEmail.isEnabled = false
            binding.etPassword.isEnabled = false
        } else {
            binding.btnLogin.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.isEnabled = true
            binding.etEmail.isEnabled = true
            binding.etPassword.isEnabled = true
        }
    }
}