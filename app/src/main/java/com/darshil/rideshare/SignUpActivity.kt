package com.darshil.rideshare

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.darshil.rideshare.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Register Button
        binding.btnRegister.setOnClickListener {
            if (validateInputs()) {
                performRegistration()
            }
        }

        // Sign In Link
        binding.tvSignIn.setOnClickListener {
            finish() // Go back to SignInActivity
        }
    }

    private fun validateInputs(): Boolean {
        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        // Reset errors
        binding.tilFullName.error = null
        binding.tilEmail.error = null
        binding.tilPhone.error = null
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null

        // Validate full name
        if (fullName.isEmpty()) {
            binding.tilFullName.error = getString(R.string.error_empty_name)
            binding.etFullName.requestFocus()
            return false
        }

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

        // Validate phone
        if (phone.isEmpty()) {
            binding.tilPhone.error = getString(R.string.error_empty_phone)
            binding.etPhone.requestFocus()
            return false
        }

        if (phone.length < 10) {
            binding.tilPhone.error = getString(R.string.error_invalid_phone)
            binding.etPhone.requestFocus()
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

        // Validate confirm password
        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = "Please confirm your password"
            binding.etConfirmPassword.requestFocus()
            return false
        }

        if (password != confirmPassword) {
            binding.tilConfirmPassword.error = getString(R.string.error_passwords_dont_match)
            binding.etConfirmPassword.requestFocus()
            return false
        }

        return true
    }

    private fun performRegistration() {
        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val password = binding.etPassword.text.toString()

        // Show loading
        setLoading(true)

        // TODO: Implement actual registration (Day 13 - Firebase)
        // For now, simulate network delay
        binding.root.postDelayed({
            setLoading(false)

            // Simulate successful registration
            Toast.makeText(this, getString(R.string.registration_success), Toast.LENGTH_SHORT).show()

            // Navigate to SignInActivity
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        }, 2000) // 2 second delay
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnRegister.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
            binding.btnRegister.isEnabled = false
            // Disable all inputs
            binding.etFullName.isEnabled = false
            binding.etEmail.isEnabled = false
            binding.etPhone.isEnabled = false
            binding.etPassword.isEnabled = false
            binding.etConfirmPassword.isEnabled = false
        } else {
            binding.btnRegister.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            binding.btnRegister.isEnabled = true
            // Enable all inputs
            binding.etFullName.isEnabled = true
            binding.etEmail.isEnabled = true
            binding.etPhone.isEnabled = true
            binding.etPassword.isEnabled = true
            binding.etConfirmPassword.isEnabled = true
        }
    }
}