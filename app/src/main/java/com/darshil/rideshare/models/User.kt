package com.darshil.rideshare.models

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val profilePicture: String? = null,
    val rating: Double = 0.0,
    val totalRides: Int = 0,
    val memberSince: String
)