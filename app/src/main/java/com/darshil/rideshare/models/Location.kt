package com.darshil.rideshare.models

data class Location(
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val placeId: String? = null
)