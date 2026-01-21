package com.darshil.rideshare

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.darshil.rideshare.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Login Button
        binding.btnLogin.setOnClickListener {
            if (validateInputs()) {
                performLogin()
            }
        }

        // Forgot Password
        binding.btnForgotPassword.setOnClickListener {
            Toast.makeText(this, "Forgot Password - Coming soon!", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to ForgotPasswordActivity
        }

        // Sign Up Link
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
        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.error_empty_email)
            binding.etEmail.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = getString(R.string.error_invalid_email)
            binding.etEmail.requestFocus()
            return false
        }

        // Validate password
        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.error_empty_password)
            binding.etPassword.requestFocus()
            return false
        }

        if (password.length < 6) {
            binding.tilPassword.error = getString(R.string.error_short_password)
            binding.etPassword.requestFocus()
            return false
        }

        return true
    }

    private fun performLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString()

        // Show loading
        setLoading(true)

        // TODO: Implement actual authentication (Day 13 - Firebase)
        // For now, simulate network delay
        binding.root.postDelayed({
            setLoading(false)

            // Simulate successful login
            Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()

            // Navigate to MainActivity (or HomeActivity)
            val intent = Intent(this, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        }, 2000) // 2 second delay
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