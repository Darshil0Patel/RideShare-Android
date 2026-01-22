package com.darshil.rideshare.fragments

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darshil.rideshare.R
import com.darshil.rideshare.RideHistoryAdapter
import com.darshil.rideshare.databinding.FragmentRidesBinding
import com.darshil.rideshare.models.Ride
import com.darshil.rideshare.models.RideStatus
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class RidesFragment : Fragment() {

    private var _binding: FragmentRidesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RideHistoryAdapter
    private val allRides = mutableListOf<Ride>()
    private var currentTab = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRidesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadDummyData()
        setupRecyclerView()
        setupTabs()
    }

    private fun loadDummyData() {
        allRides.addAll(
            listOf(
                Ride(
                    id = "ride1",
                    userId = "user123",
                    driverId = "driver1",
                    pickupAddress = "123 Main Street, NYC",
                    pickupLatitude = 40.7128,
                    pickupLongitude = -74.0060,
                    dropAddress = "456 Park Avenue, NYC",
                    dropLatitude = 40.7589,
                    dropLongitude = -73.9851,
                    rideType = "economy",
                    estimatedFare = 25.50,
                    actualFare = 25.50,
                    distance = 5.2,
                    duration = 15,
                    status = RideStatus.COMPLETED,
                    requestTime = "2024-01-15T14:30:00",
                    startTime = "2024-01-15T14:35:00",
                    endTime = "2024-01-15T14:50:00"
                ),
                Ride(
                    id = "ride2",
                    userId = "user123",
                    driverId = "driver2",
                    pickupAddress = "789 Broadway, NYC",
                    pickupLatitude = 40.7300,
                    pickupLongitude = -74.0000,
                    dropAddress = "321 Fifth Avenue, NYC",
                    dropLatitude = 40.7500,
                    dropLongitude = -73.9800,
                    rideType = "premium",
                    estimatedFare = 35.00,
                    actualFare = 35.00,
                    distance = 7.5,
                    duration = 20,
                    status = RideStatus.COMPLETED,
                    requestTime = "2024-01-14T10:15:00",
                    startTime = "2024-01-14T10:20:00",
                    endTime = "2024-01-14T10:40:00"
                ),
                Ride(
                    id = "ride3",
                    userId = "user123",
                    driverId = null,
                    pickupAddress = "555 Madison Avenue, NYC",
                    pickupLatitude = 40.7550,
                    pickupLongitude = -73.9750,
                    dropAddress = "888 Lexington Avenue, NYC",
                    dropLatitude = 40.7650,
                    dropLongitude = -73.9650,
                    rideType = "economy",
                    estimatedFare = 18.00,
                    actualFare = null,
                    distance = 3.5,
                    duration = 10,
                    status = RideStatus.CANCELLED,
                    requestTime = "2024-01-13T16:45:00"
                ),
                Ride(
                    id = "ride4",
                    userId = "user1234",
                    driverId = null,
                    pickupAddress = "999 Park Lane, NYC",
                    pickupLatitude = 40.7550,
                    pickupLongitude = -73.9750,
                    dropAddress = "111 Ocean Drive, NYC",
                    dropLatitude = 40.7650,
                    dropLongitude = -73.9650,
                    rideType = "economy",
                    estimatedFare = 18.00,
                    actualFare = null,
                    distance = 3.5,
                    duration = 10,
                    status = RideStatus.REQUESTED,
                    requestTime = "2024-01-13T16:45:00"
                )
            )
        )
    }

    private fun setupRecyclerView() {
        val initialRides = getActiveRides()

        adapter = RideHistoryAdapter(initialRides.toMutableList()) { ride ->
            Toast.makeText(
                requireContext(),
                "Clicked ride to ${ride.dropAddress}",
                Toast.LENGTH_SHORT
            ).show()
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val ride = adapter.rides[position]

                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Ride")
                    .setMessage("Delete ride from ${ride.pickupAddress} to ${ride.dropAddress}?")
                    .setPositiveButton("Delete") { _, _ ->
                        performDelete(position, ride)
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        adapter.notifyItemChanged(position)
                        dialog.dismiss()
                    }
                    .setOnCancelListener {
                        adapter.notifyItemChanged(position)
                    }
                    .show()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView

                    // Draw red background
                    val background = ColorDrawable(Color.parseColor("#F44336"))
                    background.setBounds(
                        itemView.right + dX.toInt(),
                        itemView.top,
                        itemView.right,
                        itemView.bottom
                    )
                    background.draw(c)

                    // Draw delete icon
                    val deleteIcon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_delete  // ✅ This should work now
                    )

                    deleteIcon?.let { icon ->
                        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
                        val iconTop = itemView.top + iconMargin
                        val iconBottom = iconTop + icon.intrinsicHeight
                        val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth - 20
                        val iconRight = itemView.right - iconMargin - 20

                        // Set white tint
                        icon.setTint(Color.WHITE)
                        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        icon.draw(c)
                    }
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@RidesFragment.adapter
            setHasFixedSize(true)
        }

        updateEmptyState()
    }

    private fun performDelete(position: Int, ride: Ride) {
        val deletedRide = adapter.removeItem(position)
        allRides.remove(deletedRide)
        updateEmptyState()

        Snackbar.make(
            binding.recyclerView,
            "Ride deleted",
            Snackbar.LENGTH_LONG
        ).setAction("Undo") {
            allRides.add(deletedRide)
            refreshCurrentTab()
            Toast.makeText(requireContext(), "Ride restored", Toast.LENGTH_SHORT).show()
        }.show()
    }

    private fun setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentTab = tab?.position ?: 0
                when (currentTab) {
                    0 -> showActiveRides()
                    1 -> showCompletedRides()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun showActiveRides() {
        val activeRides = getActiveRides()
        adapter.updateData(activeRides)
        updateEmptyState()
    }

    private fun showCompletedRides() {
        val completedRides = getCompletedRides()
        adapter.updateData(completedRides)
        updateEmptyState()
    }

    private fun refreshCurrentTab() {
        when (currentTab) {
            0 -> showActiveRides()
            1 -> showCompletedRides()
        }
    }

    private fun getActiveRides(): List<Ride> {
        return allRides.filter {
            it.status != RideStatus.COMPLETED && it.status != RideStatus.CANCELLED
        }
    }

    private fun getCompletedRides(): List<Ride> {
        return allRides.filter {
            it.status == RideStatus.COMPLETED || it.status == RideStatus.CANCELLED
        }
    }

    private fun updateEmptyState() {
        val isEmpty = adapter.itemCount == 0

        if (isEmpty) {
            binding.recyclerView.visibility = View.GONE
            binding.tvNoRides.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.tvNoRides.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}