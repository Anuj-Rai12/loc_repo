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
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtesting.R
import com.example.androidtesting.TAG
import com.google.android.material.snackbar.Snackbar
import com.vmadalin.easypermissions.EasyPermissions
import java.lang.Exception
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

class MessageDialog constructor(
    private val title: String,
    private val message: String,
    private val btnName: String,
    private val itemClicked: (() -> Unit?)? = null
) :
    androidx.fragment.app.DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alterDialog = AlertDialog.Builder(requireActivity()).setTitle(title)
        alterDialog.setMessage(message).setIcon(android.R.drawable.ic_dialog_info)
        if (btnName == "ok") {
            alterDialog.setPositiveButton(btnName) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
        } else {
            alterDialog.setPositiveButton(btnName) { dialogInterface, _ ->
                itemClicked?.invoke()
                dialogInterface.dismiss()
            }
            alterDialog.setNeutralButton(FilesUtils.cancel) { dialog, _ ->
                dialog.dismiss()
            }
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
            Log.i(TAG, "getDeviceId: Error Found -> ${e.localizedMessage}")
            Settings.Secure.getString(
                contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }
    }
    return deviceId
}


@RequiresApi(Build.VERSION_CODES.M)
fun Activity.retryMsg(
    title: String = "No InterNet Connection Found Try Again",
    setAction: String = "RETRY",
    response: () -> Unit,
    length: Int = Snackbar.LENGTH_LONG
) {
    val snackBar = Snackbar.make(findViewById(android.R.id.content), title, length)
    setAction.let {
        snackBar.setAction(it) {
            response()
        }.setActionTextColor(resources.getColor(R.color.red, null))
    }
    snackBar.show()
}


object FilesUtils {
    const val BASEUrl = "http://143.244.138.96:2110/api/"
    const val POST_REQ = "status"
    const val contextType = "Content-Type: application/json"
    const val ACTION_SET_REPETITIVE_EXACT = "ACTION_SET_REPETITIVE_EXACT"
    const val ACTION_SET_EXACT = "ACTION_SET_EXTRA"
    const val ExTRA_ALARM_DURATION = "ALARM_DURATION"
    const val repeatTime = 300000
    const val cancel = "Cancel"
    const val retryBtn = "Retry"
}