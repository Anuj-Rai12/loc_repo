package com.example.androidtesting.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponseCls(
    @SerializedName("battery")
    val battery: String,

    @SerializedName("charging")
    val charging: String,

    @SerializedName("device")
    val device: String,

    @SerializedName("internet-connected")

    val internetConnected: String,

    @SerializedName("message")

    val message: String,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("time-stamp")
    val timeStamp: String
)