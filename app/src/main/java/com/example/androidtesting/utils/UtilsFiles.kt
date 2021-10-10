package com.example.androidtesting.utils

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidtesting.R
import com.vmadalin.easypermissions.EasyPermissions
import java.text.DateFormat

const val FINE_LOCATION = 101
const val COARSE_LOCATION = 102
const val BACK_LOCATION = 201

const val URL = "https://www.google.com/maps/search/?api=1&query="
fun Activity.checkCoarsePermission() =
    EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)

@RequiresApi(Build.VERSION_CODES.Q)
fun Activity.checkBackPermission() =
    EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)

fun Activity.checkFinePermission() =
    EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)

fun Context.msg(msg: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, time).show()
}

class Dialog(private val title: String, private val msg: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alterDialog =
            AlertDialog.Builder(requireActivity()).setTitle(title).setIcon(R.drawable.loction)
        alterDialog.setMessage(msg)
            .setPositiveButton("ok") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
        return alterDialog.create()
    }
}
@Entity(tableName = "User_Entity_table")
data class UserLocation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val longitude: String,
    val latitude: String,
    val create: Long = System.currentTimeMillis()
) {
    val timestamp: String
        get() = DateFormat.getDateTimeInstance().format(create)
}


fun Activity.shareText(title: String, message: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plan"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    startActivity(Intent.createChooser(intent, title))
}