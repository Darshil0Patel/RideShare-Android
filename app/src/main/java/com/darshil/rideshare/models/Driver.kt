package com.darshil.rideshare.models

data class Driver(
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val profilePicture: String? = null,
    val vehicleNumber: String,
    val vehicleModel: String,
    val vehicleType: String, // "economy", "premium", "xl"
    val vehicleColor: String,
    val rating: Double = 0.0,
    val totalRides: Int = 0,
    val isAvailable: Boolean = true,
    val currentLatitude: Double = 0.0,
    val currentLongitude: Double = 0.0
)