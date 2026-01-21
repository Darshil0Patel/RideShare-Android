package com.darshil.rideshare.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.darshil.rideshare.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupClickListeners()
    }

    private fun setupViews() {
        // TODO: Load user data and display welcome message
        binding.tvWelcome.text = "Welcome, User!"
    }

    private fun setupClickListeners() {
        binding.btnBookRide.setOnClickListener {
            Toast.makeText(requireContext(), "Book Ride - Coming in Day 15!", Toast.LENGTH_SHORT).show()
        }

        binding.btnScheduleRide.setOnClickListener {
            Toast.makeText(requireContext(), "Schedule Ride - Coming soon!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}