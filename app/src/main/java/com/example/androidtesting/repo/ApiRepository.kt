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
import retrofit2.HttpException
import javax.inject.Inject

class ApiRepository @Inject constructor(private val myPostApi: MyPostApi) {

    fun uploadDeviceData(localData: LocalData) = flow {
        emit(MySealedFile.Loading("Loading Data ..."))
        kotlinx.coroutines.delay(2000)
        val response = try {
            val uploadData = ApiUploadData(
                battery = localData.battery,
                charging = localData.charging,
                device = localData.device,
                internetConnected = localData.internetConnected,
                timeStamp = localData.timestamp
            )
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