package com.darshil.rideshare

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.darshil.rideshare.databinding.ActivitySignUpBinding
import com.darshil.rideshare.utils.ValidationHelper
import com.darshil.rideshare.utils.ValidationResult
import com.darshil.rideshare.utils.ValidationTextWatcher

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupRealtimeValidation()
        setupPasswordStrengthIndicator()
        setupClickListeners()
    }

    private fun setupRealtimeValidation() {
        // Real-time name validation
        binding.etFullName.addTextChangedListener(
            ValidationTextWatcher(binding.tilFullName) { name ->
                ValidationHelper.validateName(name)
            }
        )

        // Real-time email validation
        binding.etEmail.addTextChangedListener(
            ValidationTextWatcher(binding.tilEmail) { email ->
                ValidationHelper.validateEmail(email)
            }
        )

        // Real-time phone validation
        binding.etPhone.addTextChangedListener(
            ValidationTextWatcher(binding.tilPhone) { phone ->
                ValidationHelper.validatePhone(phone)
            }
        )

        // Real-time password validation
        binding.etPassword.addTextChangedListener(
            ValidationTextWatcher(binding.tilPassword) { password ->
                ValidationHelper.validateStrongPassword(password)
            }
        )

        // Real-time confirm password validation
        binding.etConfirmPassword.addTextChangedListener(
            ValidationTextWatcher(binding.tilConfirmPassword) { confirmPassword ->
                val password = binding.etPassword.text.toString()
                ValidationHelper.validatePasswordMatch(password, confirmPassword)
            }
        )
    }

    private fun setupPasswordStrengthIndicator() {
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString()

                // Show/hide indicator
                if (password.isEmpty()) {
                    binding.llPasswordStrength.visibility = View.GONE
                    return
                } else {
                    binding.llPasswordStrength.visibility = View.VISIBLE
                }

                // Calculate strength
                val strength = calculatePasswordStrength(password)

                // Update progress
                binding.pbStrength.progress = strength

                // Update color and text
                when (strength) {
                    in 0..25 -> {
                        binding.tvStrengthLabel.text = "Weak"
                        binding.tvStrengthLabel.setTextColor(Color.RED)
                        binding.pbStrength.progressTintList = ColorStateList.valueOf(Color.RED)
                    }
                    in 26..50 -> {
                        binding.tvStrengthLabel.text = "Fair"
                        binding.tvStrengthLabel.setTextColor(Color.parseColor("#FF6B00")) // Orange
                        binding.pbStrength.progressTintList = ColorStateList.valueOf(Color.parseColor("#FF6B00"))
                    }
                    in 51..75 -> {
                        binding.tvStrengthLabel.text = "Good"
                        binding.tvStrengthLabel.setTextColor(Color.parseColor("#FFB300")) // Yellow
                        binding.pbStrength.progressTintList = ColorStateList.valueOf(Color.parseColor("#FFB300"))
                    }
                    in 76..100 -> {
                        binding.tvStrengthLabel.text = "Strong"
                        binding.tvStrengthLabel.setTextColor(Color.GREEN)
                        binding.pbStrength.progressTintList = ColorStateList.valueOf(Color.GREEN)
                    }
                }
            }
        })
    }

    private fun calculatePasswordStrength(password: String): Int {
        var score = 0

        // Length check (0-25 points)
        when {
            password.length >= 12 -> score += 25
            password.length >= 8 -> score += 20
            password.length >= 6 -> score += 10
            else -> score += 5
        }

        // Uppercase letter (25 points)
        if (password.any { it.isUpperCase() }) {
            score += 25
        }

        // Lowercase letter (25 points)
        if (password.any { it.isLowerCase() }) {
            score += 25
        }

        // Digit (25 points)
        if (password.any { it.isDigit() }) {
            score += 25
        }

        // Special character (25 points) - FIXED REGEX
        if (password.any { "!@#$%^&*(),.?\":{}|<>".contains(it) }) {
            score += 25
        }

        // Bonus: Mix of all types
        val hasUpper = password.any { it.isUpperCase() }
        val hasLower = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecial = password.any { "!@#$%^&*(),.?\":{}|<>".contains(it) }

        if (hasUpper && hasLower && hasDigit && hasSpecial) {
            score = minOf(100, score + 10)  // Bonus but cap at 100
        }

        return minOf(score, 100)  // Cap at 100
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            if (validateAllInputs()) {
                performRegistration()
            }
        }

        binding.tvSignIn.setOnClickListener {
            finish()
        }
    }

    private fun validateAllInputs(): Boolean {
        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        // Validate full name
        when (val result = ValidationHelper.validateName(fullName)) {
            is ValidationResult.Error -> {
                binding.tilFullName.error = result.message
                binding.etFullName.requestFocus()
                return false
            }
            is ValidationResult.Success -> binding.tilFullName.error = null
        }

        // Validate email
        when (val result = ValidationHelper.validateEmail(email)) {
            is ValidationResult.Error -> {
                binding.tilEmail.error = result.message
                binding.etEmail.requestFocus()
                return false
            }
            is ValidationResult.Success -> binding.tilEmail.error = null
        }

        // Validate phone
        when (val result = ValidationHelper.validatePhone(phone)) {
            is ValidationResult.Error -> {
                binding.tilPhone.error = result.message
                binding.etPhone.requestFocus()
                return false
            }
            is ValidationResult.Success -> binding.tilPhone.error = null
        }

        // Validate password
        when (val result = ValidationHelper.validateStrongPassword(password)) {
            is ValidationResult.Error -> {
                binding.tilPassword.error = result.message
                binding.etPassword.requestFocus()
                return false
            }
            is ValidationResult.Success -> binding.tilPassword.error = null
        }

        // Validate confirm password
        when (val result = ValidationHelper.validatePasswordMatch(password, confirmPassword)) {
            is ValidationResult.Error -> {
                binding.tilConfirmPassword.error = result.message
                binding.etConfirmPassword.requestFocus()
                return false
            }
            is ValidationResult.Success -> binding.tilConfirmPassword.error = null
        }

        return true
    }

    private fun performRegistration() {
        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val password = binding.etPassword.text.toString()

        setLoading(true)

        // TODO: Implement actual registration (Day 13 - Firebase)
        binding.root.postDelayed({
            setLoading(false)

            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

            // Navigate to SignInActivity
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        }, 2000)
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnRegister.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
            binding.btnRegister.isEnabled = false
            binding.etFullName.isEnabled = false
            binding.etEmail.isEnabled = false
            binding.etPhone.isEnabled = false
            binding.etPassword.isEnabled = false
            binding.etConfirmPassword.isEnabled = false
        } else {
            binding.btnRegister.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            binding.btnRegister.isEnabled = true
            binding.etFullName.isEnabled = true
            binding.etEmail.isEnabled = true
            binding.etPhone.isEnabled = true
            binding.etPassword.isEnabled = true
            binding.etConfirmPassword.isEnabled = true
        }
    }
}