package com.example.afya.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afya.adapter.TripsAdapter
import com.example.afya.data.model.User
import com.example.afya.databinding.FragmentTripsBinding
import com.example.afya.view.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class TripsFragment : Fragment() {

    private var columnCount = 1
    private lateinit var binding: FragmentTripsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentTripsBinding.inflate(inflater, container, false)

        // Set the adapter
        val adapter = TripsAdapter {
            //Go to the detail fragment with the id of the item
            validate()
        }

        with(binding.list) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            this.adapter = adapter
        }
        adapter.submitList(PlaceholderContent.ITEMS)

        binding.button.setOnClickListener {
            communicateWithBlutooth()
        }

        return binding.root
    }

    private fun validate() {
        this.findNavController().navigate(
            TripsFragmentDirections.actionTripsFragmentToExerciceDetailsFragment6()
        )
    }

    fun communicateWithBlutooth(){
        this.findNavController().navigate(
            TripsFragmentDirections.actionTripsFragmentToBluetoothCommunicationFragment2()
        )
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TripsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}