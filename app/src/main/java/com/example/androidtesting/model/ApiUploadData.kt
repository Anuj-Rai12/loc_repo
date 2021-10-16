package com.example.androidtesting.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.DateFormat

data class ApiUploadData(
    @SerializedName("device")
    val device: String,

    @SerializedName("internet-connected")
    val internetConnected: String,

    @SerializedName("charging")
    val charging: String,

    @SerializedName("battery")
    val battery: String,

    @SerializedName("time-stamp")
    val timeStamp: String

)


data class LocalData(
    val battery: String,
    val charging: String,
    val device: String,
    val internetConnected: String,
    val create: Long = System.currentTimeMillis()
) {
    val timestamp: String
        get() = DateFormat.getDateTimeInstance().format(create)
}