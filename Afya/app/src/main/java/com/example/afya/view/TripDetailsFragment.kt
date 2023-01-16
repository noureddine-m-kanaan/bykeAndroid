package com.example.afya.view

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.afya.data.model.Trip
import com.example.afya.databinding.FragmentTripDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*

class TripDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTripDetailsBinding
    private lateinit var trip: Trip
    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null

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

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            // Set the properties of the map
            it.uiSettings.isZoomControlsEnabled = true
            it.uiSettings.isMapToolbarEnabled = true
            it.setMinZoomPreference(5f)
            it.setMaxZoomPreference(15f)
            it.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(trip.etapesByNum_sortie?.get(1)?.latitude!!.toDouble(),
                        trip.etapesByNum_sortie?.get(1)?.longitude!!.toDouble()),
                    12f
                )
            )
            val polyline: MutableList<LatLng> = ArrayList()

                for(step in trip.etapesByNum_sortie!!) {
                    val pos = LatLng(step.latitude!!.toDouble(), step.longitude!!.toDouble())
                    it.addMarker(
                        MarkerOptions()
                            .position(pos)
                            .title(step.nom_etape)
                    )
                    polyline.add(pos)
                }
            val bounds = polyline.fold ( LatLngBounds.builder()) { builder, it -> builder.include(it) }
            polyline.add(polyline[0])
            it.addPolyline(PolylineOptions().addAll(polyline))
            it.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}
