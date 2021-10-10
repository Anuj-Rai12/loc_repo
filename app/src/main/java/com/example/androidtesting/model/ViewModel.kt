package com.example.androidtesting.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidtesting.db.LocDao
import com.example.androidtesting.utils.UserLocation
import kotlinx.coroutines.launch

class ViewModel(private val locDao: LocDao) :
    ViewModel() {

    val dbData = locDao.getData().asLiveData()

    fun setLocationData(userLocation: UserLocation) = viewModelScope.launch {
        locDao.insertData(userLocation)
    }
}