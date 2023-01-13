package com.example.afya.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beust.klaxon.Klaxon
import com.example.afya.api.API
import com.example.afya.bluetooth.BluetoothService
import com.example.afya.data.Step
import com.example.afya.data.Trip
import com.example.afya.database.MyDatabase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Time
import java.util.*

class BluetoothCommunicationViewModel : ViewModel() {
    private val _tripNum = MutableLiveData<Int>()
    val tripNum: MutableLiveData<Int>
        get() = _tripNum

    private val _numUtil = MutableLiveData<Int>()
    val numUtil: MutableLiveData<Int>
        get() = _numUtil

    private val _dateTrip = MutableLiveData<Date>()
    val dateTrip: MutableLiveData<Date>
        get() = _dateTrip

    private val _startTime = MutableLiveData<Time>()
    val startTime: MutableLiveData<Time>
        get() = _startTime

    private val _endTime = MutableLiveData<Time>()
    val endTime: MutableLiveData<Time>
        get() = _endTime

    private val _distance = MutableLiveData<Double>()
    val distance: MutableLiveData<Double>
        get() = _distance

    private val _steps : MutableList<Step> =ArrayList()
    val steps: MutableList<Step>
        get() = _steps

    private val _trip = MutableLiveData<Trip>()
    val trip: MutableLiveData<Trip>
        get() = _trip

    init {
        receive()
    }


    fun receive() {
        _dateTrip.postValue(Calendar.getInstance().time)
        var cpt = 0
        val numSteps : Int = 0
        BluetoothService.msg.observeForever {
            msg->
            //Toast.makeText(this., msg, Toast.LENGTH_SHORT).show()
            Log.v("msg :", msg)
           /* try {
                if(cpt < numSteps)
                {
                    val step = Klaxon().parse<Step>(msg)
                    _steps.add(step!!)
                    if(_steps.size >= 2)
                        _distance.postValue(_distance.value!! + distance(_steps[_steps.size-1],_steps[_steps.size-2]))
                    cpt++
                } else {
                    saveTrip()
                }
            }catch (e: Exception){
                try {
                    val numSteps = Integer.parseInt(msg)
                }catch (exc:java.lang.Exception) {
                    val _startTime = Time.valueOf(msg)
                }
            }*/

        }

    }

    private fun saveTrip() {
        val myTrip  :Trip = Trip(
            _tripNum.value!!,
            _numUtil.value!!,
            _dateTrip.value!!,
            _startTime.value!!,
            _endTime.value!!,
            _distance.value!!,
            _steps!!
        )

        viewModelScope.launch(Dispatchers.IO) {
            val req = Retrofit.Builder()
                .baseUrl("http://192.168.60.26:8080/")// changer l'adresse ip
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)
                .addTrip(myTrip)
            if (req.isSuccessful) {
                _trip.postValue(myTrip)
            } else {
                return@launch
            }
        }
    }


    fun distance(step1: Step, step2: Step): Double {
        val lat1: Double = step1.latitude
        val lon1: Double = step1.longitude
        val lat2: Double = step2.latitude
        val lon2: Double = step2.longitude
        val earthRadius = 6371.0 // in kilometers
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = (Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val distance = earthRadius * c
        return distance * 1000 // in meters
    }

    fun reset() {
        //BluetoothService.msg.removeObservers(BluetoothService.msg)
    }

    companion object {
        fun provideFactory(
            userID: Long = 0L
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                if (modelClass.isAssignableFrom(BluetoothCommunicationViewModel::class.java)) {
                    val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                    val dataSource = MyDatabase.getInstance(application).tripDao
                    return BluetoothCommunicationViewModel() as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }





}