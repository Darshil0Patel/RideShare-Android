package com.darshil.rideshare.models

data class Ride(
    val id: String,
    val userId: String,
    val driverId: String?,
    val pickupAddress: String,
    val pickupLatitude: Double,
    val pickupLongitude: Double,
    val dropAddress: String,
    val dropLatitude: Double,
    val dropLongitude: Double,
    val rideType: String, // "economy", "premium", "xl"
    val estimatedFare: Double,
    val actualFare: Double? = null,
    val distance: Double, // in kilometers
    val duration: Int, // in minutes
    val status: RideStatus,
    val requestTime: String,
    val startTime: String? = null,
    val endTime: String? = null,
    val paymentMethod: String = "cash",
    val rating: Double? = null,
    val review: String? = null
)

enum class RideStatus {
    REQUESTED,      // User requested ride
    ACCEPTED,       // Driver accepted
    ARRIVING,       // Driver on the way to pickup
    PICKED_UP,      // Ride started
    COMPLETED,      // Ride ended
    CANCELLED       // Ride cancelled
}