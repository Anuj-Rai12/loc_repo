package com.example.androidtesting.database

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidtesting.TAG
import com.example.androidtesting.utils.UserLocation
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCES_USER = "User_INFO"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_USER)


class Locations constructor(private val context: Context) {

    val read = context.dataStore.data.catch { e ->
        if (e is IOException) {
            Log.i(TAG, "READ_EXCEPTION: ${e.localizedMessage}")
            emit(emptyPreferences())
        } else {
            throw e
        }
    }.map { preferences ->
        val longitude = preferences[data.LONG] ?: ""
        val latitude = preferences[data.LAT] ?: ""
        val time = preferences[data.time] ?: 0.toLong()
        UserLocation(
            longitude = longitude,
            latitude = latitude,
            create = time
        )
    }

    private object data {
        val LONG = stringPreferencesKey("Longitude")
        val LAT = stringPreferencesKey("Latitude")
        val time = longPreferencesKey("TimeStamp")

    }

    suspend fun addTime(userLocation: UserLocation) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[data.LONG] = userLocation.longitude
            mutablePreferences[data.LAT] = userLocation.latitude
            mutablePreferences[data.time] = userLocation.create
        }
    }
}