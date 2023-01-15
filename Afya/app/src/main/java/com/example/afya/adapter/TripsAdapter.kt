package com.example.afya.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afya.R
import com.example.afya.data.model.Trip
import com.example.afya.view.TripsFragment.OnTripClickListener

class TripsAdapter(private val trips: List<Trip>) : RecyclerView.Adapter<TripsAdapter.TripsViewHolder>() {
    private var listener: OnTripClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trip, parent, false)
        return TripsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripsViewHolder, position: Int) {
        val trip = trips[position]
        holder.bind(trip)
        holder.buttonDetails.setOnClickListener {
            listener?.onTripClick(trip)
        }
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    inner class TripsViewHolder(tripView: View) : RecyclerView.ViewHolder(tripView) {
        private val dateTrip: TextView = tripView.findViewById(R.id.tv_dateTrip)
        val buttonDetails: Button = tripView.findViewById(R.id.buttonDetails)
        fun bind(trip: Trip) {
            dateTrip.text = trip.date_sortie.toString()
        }
    }

    fun setOnTripClickListener(listener: OnTripClickListener) {
        this.listener = listener
    }

}