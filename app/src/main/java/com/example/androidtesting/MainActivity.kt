package com.example.androidtesting

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.androidtesting.databinding.ActivityMainBinding
import com.example.androidtesting.unit.*
import com.example.androidtesting.viewmodel.MyViewModel
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.os.BatteryManager

import android.content.Intent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import com.example.androidtesting.model.LocalData


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var binding: ActivityMainBinding
    private var alertDialog: MessageDialog? = null
    private var charringFlag: Boolean? = null
    private var imeiData: String? = null
    private var chargeLevel: String? = null

    @Inject
    lateinit var networkSetting: NetworkSetting

    @Inject
    lateinit var customProgress: CustomProgress

    private val viewModel: MyViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        savedInstanceState?.let {
            chargeLevel = it.getString(FilesUtils.ACTION_SET_EXACT)
            charringFlag = it.getBoolean(FilesUtils.ACTION_SET_REPETITIVE_EXACT)
        }
        setContentView(binding.root)
        grantPermission()
        getIMEI()
        viewModel.imeiId.asLiveData().observe(this) {
            it?.let { ime ->
                if (ime.isNotBlank() || ime.isNotEmpty()) {
                    imeiData = ime
                }
            }
        }

        binding.getInfoBtn.setOnClickListener {
            getBattery()
            if (networkSetting.isConnected()) {
                if (charringFlag == null || chargeLevel == null || imeiData == null) {
                    this.msg("UnExpected Error")
                    return@setOnClickListener
                } else {
                    uploadData()
                }
            } else {
                this.retryMsg(response = {
                    if (networkSetting.isConnected()) {
                        if (charringFlag == null || chargeLevel == null || imeiData == null) {
                            this.msg("UnExpected Error")
                        } else {
                            uploadData()
                        }
                    }
                })
            }
        }
    }

    private fun uploadData() {
        val local = LocalData(
            battery = chargeLevel.toString(),
            charging = charringFlag.toString(),
            device = imeiData.toString(),
            internetConnected = networkSetting.isConnected().toString()
        )
        Log.i(TAG, "uploadData: The Answers is -> $local ")
    }

    private fun getBattery() {
        this.registerReceiver(this.broadcastReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getIMEI() {
        if (this.checkPhoneStatus()) {
            viewModel.imeiId.value = applicationContext.getDeviceId()
        }
    }


    private fun grantPermission() {
        if (!this.checkPhoneStatus())
            request()
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            Log.i(TAG, "onReceive: Battery Percentage   = $level %")
            chargeLevel = level.toString()
            val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status ==
                    BatteryManager.BATTERY_STATUS_FULL
            if (isCharging) {
                charringFlag = isCharging
                Log.i(TAG, "onReceive: Charger connected, Battery Charging..")
            } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                charringFlag = false
                Log.i(TAG, "onReceive: Charger disconnected")
            }
        }
    }


    private fun request(
        manifest: String = Manifest.permission.READ_PHONE_STATE,
        code: Int = PHONE_ID,
        name: String = "Phone Information"
    ) =
        EasyPermissions.requestPermissions(
            this,
            "Kindly Give us $name permission,otherwise application may not work Properly.",
            code,
            manifest,
        )

    private fun showLoading(string: String) = customProgress.showLoading(this, string)
    private fun hideLoading() = customProgress.hideLoading()

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        perms.forEach {
            if (EasyPermissions.permissionPermanentlyDenied(this, it)) {
                SettingsDialog.Builder(this).build().show()
            } else
                grantPermission()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        alertDialog?.dismiss()
        hideLoading()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.i(TAG, "onPermissionsGranted: Permission Granted $requestCode, and $perms")
        getIMEI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        chargeLevel?.let { outState.putString(FilesUtils.ACTION_SET_EXACT, it) }
        charringFlag?.let { outState.putBoolean(FilesUtils.ACTION_SET_REPETITIVE_EXACT, it) }
    }
}