package com.example.androidtesting

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtesting.databinding.ActivityMainBinding
import com.example.androidtesting.utils.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog


const val TAG = "ANUJ"

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var binding: ActivityMainBinding
    private var dialog: Dialog? = null
    private var data: UserLocation? = null
    private val mFusedLocation by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getLocationData()
        binding.locationClick.setOnClickListener {
            if (getLocationData()) {
                if (isLocationEnabled()) {
                    callLocation()
                } else
                    this.msg("Please Turn On Location ", Toast.LENGTH_LONG)
            } else {
                this.msg("Permission is Not Given", Toast.LENGTH_SHORT)
                getLocationData()
            }
        }
        binding.shareClick.setOnClickListener {
            if (data != null) {
                val url = "$URL${data?.latitude},${data?.longitude}"
                val message="Hi check my location on ${data?.timestamp} \n\n $url"
                this.shareText(title = "Share Location", message = message)
            } else {
                this.msg("Firstly Get your location")
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun callLocation() {
        mFusedLocation.lastLocation
            .addOnCompleteListener { task ->
                val location = task.result
                if (location == null) {
                    requestNewLocationData()
                } else {
                    setUpData(longitude = location.longitude, latitude = location.latitude)
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        // setting LocationRequest
        // on FusedLocationClient
//        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        Looper.myLooper()
            ?.let { mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback, it) }

    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation = locationResult.lastLocation
            setUpData(latitude = mLastLocation.latitude, longitude = mLastLocation.longitude)
        }
    }

    private fun setUpData(latitude: Double, longitude: Double) {
        UserLocation(
            longitude = "$longitude",
            latitude = "$latitude"
        ).also {
            data = it
            dialog = Dialog(
                "My Location",
                msg = "The Longitude is ${it.longitude} \n And Latitude is ${it.latitude}\nAt ${it.timestamp}"
            )
            dialog?.show(supportFragmentManager, TAG)
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getLocationData(): Boolean {
        if (!this.checkFinePermission()) {
            request(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION, "Device")
            return false
        }
        if (!this.checkCoarsePermission()) {
            request(Manifest.permission.ACCESS_COARSE_LOCATION, COARSE_LOCATION, "Location")
            return false
        }
        if (!this.checkBackPermission()) {
            request(
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                BACK_LOCATION,
                "Background Location"
            )
            return false
        }
        return true
    }

    private fun request(manifest: String, code: Int, name: String) =
        EasyPermissions.requestPermissions(
            this,
            "Kindly Give us $name permission,otherwise application may not work Properly.",
            code,
            manifest,
        )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        perms.forEach {
            if (EasyPermissions.permissionPermanentlyDenied(this, it)) {
                SettingsDialog.Builder(this).build().show()
            } else
                getLocationData()
        }
    }

    override fun onPause() {
        super.onPause()
        dialog?.dismiss()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.i(TAG, "onPermissionsGranted: $requestCode is given $perms")
        getLocationData()
    }
}