package com.example.androidtesting

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var binding: ActivityMainBinding
    private var alertDialog: MessageDialog? = null

    @Inject
    lateinit var networkSetting: NetworkSetting

    @Inject
    lateinit var customProgress: CustomProgress

    private val viewModel: MyViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        grantPermission()
        Log.i(TAG, "onCreate: The Imie Number is -> ${applicationContext.getDeviceId()}")

        viewModel.imeiId.asLiveData().observe(this) {
            it?.let { ime ->
                Log.i(TAG, "onCreate: ime -> -> $ime")
                this.msg("Device Info is -> $ime")
            }
        }
        binding.getInfoBtn.setOnClickListener {
            if (networkSetting.isConnected()) {
                this.msg("Device connected")
            } else {
                this.msg("No InterNet Connection Found Try Again", Toast.LENGTH_LONG)
            }
        }
    }


    private fun grantPermission() {
        if (!this.checkPhoneStatus())
            request()
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
        applicationContext.getDeviceId()
    }

}