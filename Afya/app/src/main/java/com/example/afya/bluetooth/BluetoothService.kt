package com.example.afya.bluetooth

import android.annotation.SuppressLint
import android.app.Service
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.aflak.bluetooth.Bluetooth
import me.aflak.bluetooth.interfaces.BluetoothCallback
import me.aflak.bluetooth.interfaces.DeviceCallback
import kotlin.concurrent.thread


class BluetoothService : Service() {
    private val bluetooth : Bluetooth = Bluetooth(this)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        val msg : MutableLiveData<String> = MutableLiveData()
        val conneced : LiveData<Boolean>
            get() {
                return _connected
            }
        private val _connected : MutableLiveData<Boolean> = MutableLiveData()
    }

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        super.onCreate()
        Log.i("BluetoothService", "BluetoothService")

        thread (true){
            bluetooth.onStart()
            if(!bluetooth.isEnabled){
                bluetooth.enable()
            }
            //TODO : se connecter avec l'adresse mac
            Log.i("BluetoothService", "onStart")
            bluetooth.connectToName("ESP32test")
            Log.i("BluetoothService", "connectToName")
            bluetooth.setBluetoothCallback(this.bluetoothCallback)
            Log.i("BluetoothService", "bluetoothCallback")
            bluetooth.setDeviceCallback(this.deviceCallback)
            Log.i("BluetoothService", "setDeviceCallback")
        }
    }

    @SuppressLint("MissingPermission")
    private val deviceCallback: DeviceCallback = object : DeviceCallback {
        override fun onDeviceConnected(device: BluetoothDevice?) {
            if (device != null) {
                System.out.println("Connected to device"+device.name)
                System.out.println("Connected to device with address : "+device.address)
                Log.i("device address :", device.address)
                // TODO : Récupére l'adresse mac de l'esp32
                _connected.postValue(true)
            }
        }

        override fun onDeviceDisconnected(device: BluetoothDevice?, message: String?) {
            if (device != null) {
                System.out.println("Disconnected from device"+device.name)
                _connected.postValue(false)

            }
        }

        override fun onMessage(message: ByteArray?) {
           // Log.i("message", message.toString())
            msg.postValue(String(message!!))
        }

        override fun onError(errorCode: Int) {
            System.out.println("Error code : "+errorCode)
        }

        override fun onConnectError(device: BluetoothDevice?, message: String?) {
            if (device != null) {
                System.out.println("Problem with connecting to device : "+device.name)
            }
            System.out.println("Error message : "+message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetooth.onStop()
    }

    private val bluetoothCallback: BluetoothCallback = object : BluetoothCallback {
        override fun onBluetoothTurningOn() {}
        override fun onBluetoothOn() {
        }

        override fun onBluetoothTurningOff() {
        }

        override fun onBluetoothOff() {
            if (bluetooth.isEnabled) {
            } else {
                bluetooth.enable()
            }
        }
        override fun onUserDeniedActivation() {
            Toast.makeText(applicationContext, "I need to activate bluetooth...", Toast.LENGTH_SHORT)
                .show()
        }
    }

}

