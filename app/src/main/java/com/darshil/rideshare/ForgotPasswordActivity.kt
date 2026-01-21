package com.darshil.rideshare

import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.darshil.rideshare.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup action bar
        supportActionBar?.title = "Forgot Password"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupClickListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupClickListeners() {
        // Send Reset Link Button
        binding.btnReset.setOnClickListener {
            if (validateInputs()) {
                performPasswordReset()
            }
        }

        // Back to Sign In Button
        binding.btnSignIn.setOnClickListener {
            finish() // Just go back instead of creating new activity
        }
    }

    private fun validateInputs(): Boolean {
        val email = binding.etEmail.text.toString().trim()

        // Reset error
        binding.tilEmail.error = null

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

        return true
    }

    private fun performPasswordReset() {
        val email = binding.etEmail.text.toString().trim()

        // Show loading
        setLoading(true)

        // TODO: Implement actual password reset with Firebase (Day 13)
        // For now, simulate network delay
        binding.root.postDelayed({
            setLoading(false)

            // Show success dialog
            showSuccessDialog(email)

        }, 2000) // 2 second delay
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnReset.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
            binding.btnReset.isEnabled = false
            binding.etEmail.isEnabled = false
            binding.btnSignIn.isEnabled = false
        } else {
            binding.btnReset.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            binding.btnReset.isEnabled = true
            binding.etEmail.isEnabled = true
            binding.btnSignIn.isEnabled = true
        }
    }

    private fun showSuccessDialog(email: String) {
        AlertDialog.Builder(this)
            .setTitle("✅ Email Sent!")
            .setMessage("A password reset link has been sent to:\n\n$email\n\nPlease check your inbox and spam folder.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish() // Return to SignInActivity
            }
            .setCancelable(false)
            .show()
    }
}