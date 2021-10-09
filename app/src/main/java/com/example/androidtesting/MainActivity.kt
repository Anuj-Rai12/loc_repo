package com.example.androidtesting

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.androidtesting.databinding.ActivityMainBinding
import com.example.androidtesting.utils.*
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

const val TAG = "ANUJ"

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getLocationData()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getLocationData() {
        if (!this.checkFinePermission()){
            request(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION,"Device")
        }
        if (!this.checkCoarsePermission()){
            request(Manifest.permission.ACCESS_COARSE_LOCATION, COARSE_LOCATION,"Location")
        }
        if (!this.checkBackPermission()){
            request(Manifest.permission.ACCESS_BACKGROUND_LOCATION, BACK_LOCATION,"Background Location")
        }
    }

    private fun request(manifest:String,code:Int,name:String)=EasyPermissions.requestPermissions(
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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.i(TAG, "onPermissionsGranted: $requestCode is given $perms")
        getLocationData()
    }
}