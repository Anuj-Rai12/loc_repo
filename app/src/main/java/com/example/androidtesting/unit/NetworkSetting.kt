package com.example.androidtesting.unit

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.androidtesting.MyApplication
import javax.inject.Inject

class NetworkSetting @Inject constructor() {
    @SuppressLint("MissingPermission")
    fun isConnected(): Boolean {
        var result = false // Returns connection type. false: none; true: mobile data; true: wifi
        val cm: ConnectivityManager = MyApplication.appContext().get()
            ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            result = true
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            result = true
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                            result = true
                        }
                    }
                }
            }
        } else {
            //For Low Version Phone Of Android User than Marshmallow
            cm.run {
                cm.activeNetworkInfo?.run {
                    when (type) {
                        ConnectivityManager.TYPE_WIFI -> {
                            result = true
                        }
                        ConnectivityManager.TYPE_MOBILE -> {
                            result = true
                        }
                        ConnectivityManager.TYPE_VPN -> {
                            result = true
                        }
                    }
                }
            }
        }
        return result
    }
}