package com.example.androidtesting.unit

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.vmadalin.easypermissions.EasyPermissions
import javax.inject.Inject


const val PHONE_ID = 2101

class CustomProgress @Inject constructor(private val customProgressBar: CustomProgressbar) {
    fun hideLoading() {
        customProgressBar.dismiss()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    fun showLoading(context: Context, string: String?, boolean: Boolean = false) {
        val con = context as Activity
        customProgressBar.show(con, string, boolean)
    }
}


fun Activity.checkPhoneStatus() =
    EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE)


fun Context.msg(msg: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, time).show()
}

class MessageDialog constructor(private val title: String, private val message: String) :
    androidx.fragment.app.DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alterDialog = AlertDialog.Builder(requireActivity()).setTitle(title)
        alterDialog.setMessage(message).setIcon(android.R.drawable.ic_dialog_info)
        alterDialog.setPositiveButton("ok") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        return alterDialog.create()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("HardwareIds")
fun Context.getDeviceId(): String {
    val deviceId: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
    } else {
        val mTelephony = getSystemService(AppCompatActivity.TELEPHONY_SERVICE) as TelephonyManager
        try {
            mTelephony.imei
        } catch (e: Exception) {
            Settings.Secure.getString(
                contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }
    }
    return deviceId
}

object FilesUtils {
    const val BASEUrl = "http://143.244.138.96:2110/"
    const val POST_REQ = "api/status"
    const val contextType = "Content-Type: application/json"
    const val ACTION_SET_REPETITIVE_EXACT = "ACTION_SET_REPETITIVE_EXACT"
    const val ACTION_SET_EXACT = "ACTION_SET_EXTRA"
    const val ExTRA_ALARM_DURATION = "ALARM_DURATION"
    const val repeatTime = 300000
}