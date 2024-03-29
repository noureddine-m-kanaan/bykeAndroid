package com.example.afya.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.afya.R
import com.example.afya.databinding.FragmentUserBinding

/**
 * A fragment representing a list of Items.
 */
class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        binding.userName.text = (activity as MainActivity).getExtra()?.getString("DISPLAY_NAME")

        binding.button.setOnClickListener { logout() }

        val button = (activity as MainActivity).findViewById<Button>(R.id.button)
        button.setOnClickListener {
            communicateWithBlutooth()
        }

        return binding.root
    }

    private fun logout() {
        (activity as MainActivity).logout()
        requireActivity().finish()
    }

    private fun communicateWithBlutooth(){
        findNavController().navigate(
            UserFragmentDirections.actionUserFragmentToBluetoothCommunicationFragment2()
        )
    }
}
