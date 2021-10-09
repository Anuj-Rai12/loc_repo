package com.example.androidtesting.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidtesting.utils.UserLocation
import kotlinx.coroutines.launch

class ViewModel(private val locations: Locations) : ViewModel() {

    val read = locations.read.asLiveData()

    fun setLocation(userLocation: UserLocation) = viewModelScope.launch {
        locations.addTime(userLocation)
    }
}