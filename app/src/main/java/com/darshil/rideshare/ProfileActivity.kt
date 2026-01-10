package com.darshil.rideshare

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.darshil.rideshare.databinding.ActivityProfileBinding
import com.darshil.rideshare.models.User

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    // Dummy user data
    private val currentUser = User(
        id = "user123",
        name = "John Doe",
        email = "john.doe@example.com",
        phoneNumber = "+1 234 567 8900",
        profilePicture = null,
        rating = 4.8,
        totalRides = 127,
        memberSince = "January 2024"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set title
        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Back button

        displayUserInfo()
        setupClickListeners()
    }

    private fun displayUserInfo() {
        // Display user information
        binding.tvProfileName.text = currentUser.name
        binding.tvEmail.text = currentUser.email
        binding.tvPhone.text = currentUser.phoneNumber
        binding.tvMemberSince.text = "Member since ${currentUser.memberSince}"

        // Display stats
        binding.tvRating.text = String.format("%.1f", currentUser.rating)
        binding.tvTotalRides.text = currentUser.totalRides.toString()

        // Load profile picture if available
        currentUser.profilePicture?.let {
            // TODO: Load image with Glide/Coil in future
            // For now, using default logo
        }
    }

    private fun setupClickListeners() {
        // Edit Profile
        binding.btnEditProfile.setOnClickListener {
            Toast.makeText(this, "Edit Profile - Coming in Day 3!", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to EditProfileActivity
            // val intent = Intent(this, EditProfileActivity::class.java)
            // startActivity(intent)
        }

        // Ride History
        binding.btnRideHistory.setOnClickListener {
            Toast.makeText(this, "Ride History - Coming soon!", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to RideHistoryActivity
            // val intent = Intent(this, RideHistoryActivity::class.java)
            // startActivity(intent)
        }

        // Settings
        binding.btnSettings.setOnClickListener {
            Toast.makeText(this, "Settings - Coming soon!", Toast.LENGTH_SHORT).show()
            // TODO: Navigate to SettingsActivity
            // val intent = Intent(this, SettingsActivity::class.java)
            // startActivity(intent)
        }

        // Logout
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog, _ ->
                // Clear user session
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

                // Navigate to Welcome screen
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
    }

    // Handle back button in action bar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}