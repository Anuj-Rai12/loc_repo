package com.example.androidtesting.utils

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import com.vmadalin.easypermissions.EasyPermissions

const val FINE_LOCATION=101
const val COARSE_LOCATION=102
const val BACK_LOCATION=201
fun Activity.checkCoarsePermission() =
    EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)

@RequiresApi(Build.VERSION_CODES.Q)
fun Activity.checkBackPermission() =
    EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)

fun Activity.checkFinePermission() =
    EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)