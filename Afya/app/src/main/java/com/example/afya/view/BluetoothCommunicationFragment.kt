package com.example.afya.view

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.afya.R
import com.example.afya.bluetooth.BluetoothService
import com.example.afya.databinding.FragmentBluetoothCommunicationBinding
import com.example.afya.viewmodel.BluetoothCommunicationViewModel

class BluetoothCommunicationFragment : Fragment() {

    companion object {
        fun newInstance() = BluetoothCommunicationFragment()
    }

    private lateinit var binding: FragmentBluetoothCommunicationBinding
    private lateinit var viewModel: BluetoothCommunicationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBluetoothCommunicationBinding.inflate(inflater, container, false)

        Log.i("BluetoothCommunicationFragment", "onCreateView")

        // this.viewLifecycleOwner =
        binding.loadingAnimation.visibility = View.VISIBLE

        this.viewModel = ViewModelProvider(this).get(BluetoothCommunicationViewModel::class.java)
        binding.viewModel = viewModel
        binding.loadingAnimation.visibility = View.VISIBLE
        if (viewModel.trip.value != null) {
            binding.loadingAnimation.visibility = View.GONE
            binding.textView4.visibility = View.VISIBLE
            binding.startPosition.visibility = View.VISIBLE
            binding.buttonEnregistrer.visibility = View.VISIBLE
            binding.buttonEnregistrer.setOnClickListener {
                navigateToMain()
            }
        }

        requireActivity().startService(
            Intent(
                requireContext(),
                BluetoothService::class.java
            )
        )
        return binding.root

    }

    fun navigateToMain( ){
        this.findNavController().navigate(
            BluetoothCommunicationFragmentDirections
                .actionBluetoothCommunicationFragment2ToTripsFragment()
        )
    }


}