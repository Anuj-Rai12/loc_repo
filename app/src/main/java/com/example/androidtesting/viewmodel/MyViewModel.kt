package com.example.androidtesting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.androidtesting.model.LocalData
import com.example.androidtesting.repo.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {
    val imeiId= MutableStateFlow<String?>(null)

    fun uploadData(localData: LocalData) = repository.uploadDeviceData(localData).asLiveData()

}