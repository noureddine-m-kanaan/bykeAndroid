package com.example.afya.view

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.webkit.PermissionRequest
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.afya.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener.Builder.withContext
import com.karumi.dexter.listener.single.PermissionListener



class SplashScreenActivity : AppCompatActivity() {

    companion object{
        val REQUEST_ENABLE_BT = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

       // activateBluetooth()

        // Keep the splash screen visible for this Activity
        val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun activateBluetooth() {
        val bluetoothPermission = Manifest.permission.BLUETOOTH
        val bluetoothAdminPermission = Manifest.permission.BLUETOOTH_ADMIN

        Dexter.withActivity(this)
            .withPermission(bluetoothPermission)
          //  .withPermission(bluetoothAdminPermission)
            .withListener(object : PermissionListener {
                @SuppressLint("MissingPermission")
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
                    if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled) {
                        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
                    }
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    // Handle denied permission
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: com.karumi.dexter.listener.PermissionRequest?,
                    token: PermissionToken?
                ) {
                    TODO("Not yet implemented")
                }

                fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).check()

    }

    fun askPermissions() {
        Dexter.withActivity(this).withPermissions(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@SplashScreenActivity,
                            "I need these permissions...",
                            Toast.LENGTH_SHORT
                        ).show()
                        askPermissions()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    if (token != null) {
                        token.continuePermissionRequest()
                    }
                }
            }).check()
    }

}