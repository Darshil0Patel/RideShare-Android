package com.darshil.rideshare

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darshil.rideshare.databinding.ItemRideHistoryBinding
import com.darshil.rideshare.models.Ride
import com.darshil.rideshare.models.RideStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RideHistoryAdapter(
    val rides: MutableList<Ride>,  // ✅ Changed to MutableList
    private val onRideClick: (Ride) -> Unit
) : RecyclerView.Adapter<RideHistoryAdapter.RideViewHolder>() {

    inner class RideViewHolder(
        private val binding: ItemRideHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ride: Ride) {
            // Date & Time
            binding.tvDateTime.text = formatDateTime(ride.requestTime)

            // Locations
            binding.tvPickup.text = ride.pickupAddress
            binding.tvDrop.text = ride.dropAddress

            // Fare
            val fare = ride.actualFare ?: ride.estimatedFare
            binding.tvFare.text = "$${"%.2f".format(fare)}"

            // Status
            binding.tvStatus.text = ride.status.name.replace("_", " ")
            val statusBg = when (ride.status) {
                RideStatus.COMPLETED -> R.drawable.bg_status_completed
                RideStatus.CANCELLED -> R.drawable.bg_status_cancelled
                else -> R.drawable.bg_status_ongoing
            }
            binding.tvStatus.setBackgroundResource(statusBg)

            // Driver info
            binding.tvDriverName.text = "Driver"
            binding.tvRating.text = "4.8"

            // Ride type icon
            val rideTypeIcon = when (ride.rideType) {
                "economy" -> R.drawable.ic_car
                "premium" -> R.drawable.ic_car
                "xl" -> R.drawable.ic_car
                else -> R.drawable.ic_car
            }
            binding.ivRideType.setImageResource(rideTypeIcon)

            // Click listener
            binding.root.setOnClickListener {
                onRideClick(ride)
            }
        }

        private fun formatDateTime(dateTimeString: String): String {
            return try {
                val sdf = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
                sdf.format(Date())
            } catch (e: Exception) {
                dateTimeString
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        val binding = ItemRideHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        holder.bind(rides[position])
    }

    override fun getItemCount(): Int = rides.size

    fun removeItem(position: Int): Ride {
        val removedItem = rides.removeAt(position)
        notifyItemRemoved(position)
        return removedItem
    }

    fun addItem(position: Int, ride: Ride) {
        rides.add(position, ride)
        notifyItemInserted(position)
    }

    fun updateData(newRides: List<Ride>) {
        rides.clear()
        rides.addAll(newRides)
        notifyDataSetChanged()
    }
}