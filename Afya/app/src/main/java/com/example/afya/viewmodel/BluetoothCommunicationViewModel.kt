package com.example.afya.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.afya.bluetooth.BluetoothService
import com.example.afya.data.model.Step
import com.example.afya.database.MyDatabase
import com.google.gson.Gson

import java.util.*

class BluetoothCommunicationViewModel : ViewModel() {

    val gson = Gson()
    // TODO : Voir comment avoir le numéro de la dernière sortie
    private val _tripNum = MutableLiveData<Int>()
    val tripNum: MutableLiveData<Int>
        get() = _tripNum

    private val _numUtil = MutableLiveData<Int>() // TODO (à voir avec anthony):  numéro de l'utilisateur
    val numUtil: MutableLiveData<Int>
        get() = _numUtil

    private val _dateTrip = MutableLiveData<String>()
    val dateTrip: MutableLiveData<String>
        get() = _dateTrip

    private var _startTime = MutableLiveData<String>()
    val startTime: MutableLiveData<String>
        get() = _startTime

    private val _endTime = MutableLiveData<String>()
    val endTime: MutableLiveData<String>
        get() = _endTime

    private val _distance = MutableLiveData<Double>()
    val distance: MutableLiveData<Double>
        get() = _distance
    val strDistance : MutableLiveData<String>
        get() = MutableLiveData<String>(_distance.value.toString())

    private val _steps: MutableList<Step> = ArrayList()
    val steps: MutableList<Step>
        get() = _steps

    private val _trip = MutableLiveData<Trip?>()
    val trip: MutableLiveData<Trip?>
        get() = _trip

    private var _startPosition = MutableLiveData<String>()
    var startPosition: MutableLiveData<String> = _startPosition
        get() = _startPosition

    init {
        reset()
        receive()
    }

    fun reset() {
        _tripNum.value = 0
        _numUtil.value = 0
        _dateTrip.value = ""
        _startTime.value = ""
        _endTime.value = ""
        _distance.value = 0.0
        _steps.clear()
        _startPosition.value = "Start position"
        _trip.postValue(null)
    }

    fun receive() {
        _dateTrip.postValue(Calendar.getInstance().time.toString())
        var cpt = 0
        var numSteps: Int = 0
        var startTimeReceived: Boolean = false
        Log.i("startPosition", startPosition.value.toString())
        BluetoothService.msg.observeForever { msg ->
//             Log.i("msg :", msg)
            try {
                numSteps = Integer.parseInt(msg)
                Log.i("numSteps", numSteps.toString())
            }catch (e: Exception){
                if(cpt < numSteps) {
                    try {
                        // Log.i("msg :", msg)

                        val step = gson.fromJson(msg, Step::class.java)
                        if (step != null) {
                            Log.i("step", "Etape $cpt")
                            step.nom_etape = "Etape $cpt"
                            step.numEtape = cpt
                            step.id= cpt
                            step.num_sortie = _tripNum.value
                        }
                        _steps.add(step!!)
                        if(_steps.size >= 2)
                            _distance.postValue(_distance.value!! + distance(_steps[_steps.size-1],_steps[_steps.size-2]))
                        cpt++
                    } catch (exc: java.lang.Exception) {
                        Log.i("exception", exc.toString())
                        Log.i("msg", msg)
                        if (!startTimeReceived){
                            _startTime.postValue(msg)
                            _startTime.value?.let { Log.i("startTime", it) }
                            startTimeReceived = true
                        }else{
                            _endTime.postValue(msg)
                            _endTime.value?.let { Log.i("endTime", it) }
                        }
                    }
                }else {
                    saveTrip()
                }
            }
        }
    }

    fun saveTrip() {
        if(_trip == null) {
            _trip.postValue(Trip(
                _tripNum.value!!,
                _numUtil.value!!,
                _dateTrip.value!!,
                _startTime.value!!,
                _endTime.value!!,
                _startPosition.value!!,
                _distance.value!!,
                _steps!!
            ))
            Log.i("trip", trip.value.toString())


            /*
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
            }*/
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
                    val application =
                        checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                    val dataSource = MyDatabase.getInstance(application).tripDao
                    return BluetoothCommunicationViewModel() as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }


}