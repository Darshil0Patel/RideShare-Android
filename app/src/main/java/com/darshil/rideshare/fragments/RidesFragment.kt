package com.darshil.rideshare.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.darshil.rideshare.databinding.FragmentRidesBinding
import com.google.android.material.tabs.TabLayout

class RidesFragment : Fragment() {

    private var _binding: FragmentRidesBinding? = null
    private val binding get() = _binding!!

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

        setupTabs()
    }

    private fun setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showActiveRides()
                    1 -> showCompletedRides()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun showActiveRides() {
        // TODO: Load and display active rides (Day 5 - RecyclerView)
        binding.tvNoRides.text = "No active rides"
    }

    private fun showCompletedRides() {
        // TODO: Load and display completed rides (Day 5 - RecyclerView)
        binding.tvNoRides.text = "No completed rides"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}