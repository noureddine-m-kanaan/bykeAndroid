package com.example.afya.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.afya.R
import com.example.afya.adapter.MyItemRecyclerViewAdapter
import com.example.afya.databinding.FragmentItemExerciceBinding
import com.example.afya.view.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class ItemTripFragment : Fragment() {

    private var columnCount = 1
    private lateinit var binding: FragmentItemExerciceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_exercice_list, container, false)
        binding = FragmentItemExerciceBinding.inflate(inflater, container, false)
        binding.buttonDetails.setOnClickListener {
            validate()
        }
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }
        return view
    }

    private fun validate() {
       this.findNavController().navigate(
           ItemTripFragmentDirections.actionItemExerciceFragmentToExerciceDetailsFragment6()
       )
    }


    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ItemTripFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}