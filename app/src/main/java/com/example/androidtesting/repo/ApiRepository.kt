package com.example.androidtesting.repo

import android.util.Log
import com.example.androidtesting.TAG
import com.example.androidtesting.api.MyPostApi
import com.example.androidtesting.model.ApiUploadData
import com.example.androidtesting.model.LocalData
import com.example.androidtesting.unit.MySealedFile
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class ApiRepository @Inject constructor(private val myPostApi: MyPostApi) {

    fun uploadDeviceData(localData: LocalData) = flow {
        emit(MySealedFile.Loading("Loading Data ..."))
        kotlinx.coroutines.delay(5000)
        val response = try {
            val uploadData = ApiUploadData(
                battery = localData.battery,
                charging = localData.charging,
                device = localData.device,
                internetConnected = localData.internetConnected,
                timeStamp = localData.timestamp
            )
            /*val jsonObj = JSONObject()
            jsonObj.put("device", "localData.device")
            jsonObj.put("internet-connected", "localData.internetConnected")
            jsonObj.put("charging", "localData.charging")
            jsonObj.put("battery", "localData.battery")
            jsonObj.put("time-stamp", "localData.timestamp")

            val str = jsonObj.toString()
            val request = str.toRequestBody("application/json".toMediaTypeOrNull())*/
            //Log.i(TAG, "uploadDeviceData: Request Value -> $request")

            val info = myPostApi.uploadData(uploadData)
            Log.i(TAG, "uploadDeviceData: $info")
            if (info.isSuccessful)
                MySealedFile.Success(info.body())
            else
                MySealedFile.Success(null)

        } catch (e: Exception) {
            MySealedFile.Error(null, e)
        } catch (e: HttpException) {
            MySealedFile.Error(null, e)
        }

        emit(response)
    }.flowOn(IO)

}