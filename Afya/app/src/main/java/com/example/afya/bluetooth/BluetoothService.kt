package com.example.afya.bluetooth

import android.app.Service
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.IBinder
import android.util.Log
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

    override fun onCreate() {
        super.onCreate()
        thread (true){
            bluetooth.onStart()
            Log.v("BluetoothService", "onStart")
            bluetooth.connectToName("ESP32")
            Log.v("BluetoothService", "connectToName")
            bluetooth.setDeviceCallback(this.deviceCallback)
            Log.v("BluetoothService", "setDeviceCallback")
        }

    }

    private val deviceCallback: DeviceCallback = object : DeviceCallback {
        override fun onDeviceConnected(device: BluetoothDevice?) {
            System.out.println("Connected to device"+device.toString())
        }

        override fun onDeviceDisconnected(device: BluetoothDevice?, message: String?) {
            System.out.println("Disconnected from device"+device.toString())
        }

        override fun onMessage(message: ByteArray?) {
            BluetoothService.msg.postValue(String(message!!))
        }

        override fun onError(errorCode: Int) {
            System.out.println("Error code : "+errorCode)
        }

        override fun onConnectError(device: BluetoothDevice?, message: String?) {
            System.out.println("Problem with connecting to device : "+device.toString())
            System.out.println("Error message : "+message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetooth.onStop()
    }

}

