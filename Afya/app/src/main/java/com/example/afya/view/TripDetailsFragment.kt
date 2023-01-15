package com.example.afya.view

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afya.adapter.StepsAdapter
import com.example.afya.data.model.Trip
import com.example.afya.databinding.FragmentTripDetailsBinding

class TripDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTripDetailsBinding
    private lateinit var trip: Trip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trip = TripDetailsFragmentArgs.fromBundle(requireArguments()).trip
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTripDetailsBinding.inflate(inflater, container, false)
        binding.trip = trip

        val stepsAdapter = StepsAdapter(trip.etapesByNum_sortie!!)
        binding.rvSteps.layoutManager = LinearLayoutManager(context)
        binding.rvSteps.adapter = stepsAdapter

        return binding.root
    }

}
