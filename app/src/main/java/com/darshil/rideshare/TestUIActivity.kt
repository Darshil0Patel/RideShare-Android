package com.darshil.rideshare

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.darshil.rideshare.databinding.ActivityTestUiBinding

class TestUIActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestUiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestUiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        // Button click
        binding.btnSubmit.setOnClickListener {
//            val signUp = getString(R.string.btn_sign_in)
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            // Validation
            if (name.isEmpty()) {
                binding.etName.error = "Name is required"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.etEmail.error = "Email is required"
                return@setOnClickListener
            }

            // Check if terms accepted
            if (!binding.cbAgree.isChecked) {
                Toast.makeText(this, "Please accept terms", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get selected gender
            val gender = when (binding.rgGender.checkedRadioButtonId) {
                R.id.rbMale -> "Male"
                R.id.rbFemale -> "Female"
                R.id.rbOther -> "Other"
                else -> "Not selected"
            }

            // Get notification preference
            val notificationsEnabled = binding.switchNotifications.isChecked

            // Display result
            val result = """
                Name: $name
                Email: $email
                Gender: $gender
                Notifications: ${if (notificationsEnabled) "Enabled" else "Disabled"}
            """.trimIndent()

            binding.tvResult.text = result

            // Show toast
            Toast.makeText(this, "Form submitted!", Toast.LENGTH_LONG).show()
        }

        // CheckBox listener
        binding.cbAgree.setOnCheckedChangeListener { _, isChecked ->
            binding.btnSubmit.isEnabled = isChecked
        }

        // Initially disable button
        binding.btnSubmit.isEnabled = false
    }
}