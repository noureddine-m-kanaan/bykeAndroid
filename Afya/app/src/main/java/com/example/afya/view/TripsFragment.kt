package com.example.afya.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afya.R
import com.example.afya.adapter.TripsAdapter
import com.example.afya.api.API
import com.example.afya.data.model.Trip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A fragment representing a list of Items.
 */
class TripsFragment : Fragment() {
    lateinit var adapter: TripsAdapter
    lateinit var recyclerView: RecyclerView
    var trips: MutableList<Trip> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity: MainActivity = activity as MainActivity
        val extras = activity.getExtra()
        val id = extras?.getInt("USER_ID")
        val token = extras?.getString("TOKEN")

        val view = inflater.inflate(R.layout.fragment_trips, container, false)
        recyclerView = view.findViewById(R.id.rv_trips)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = TripsAdapter(trips)
        recyclerView.adapter = adapter

        val req = Retrofit.Builder()
            .baseUrl("http://192.168.60.26:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try{
                val response = req.getTrips(token!!, id!!)
                response.body()!!.forEach{
                    it.formatDate()
                }
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        trips.clear()
                        trips.addAll(response.body()!!)
                        adapter.notifyDataSetChanged()
                    }
                }
            }catch(e: Exception) {
                    Log.e(TAG, "Failed to fetch trips", e)
            }
        }

        return view
    }

    private fun validate() {
        this.findNavController().navigate(
            TripsFragmentDirections.actionTripsFragmentToExerciceDetailsFragment6()
        )
    }
}
