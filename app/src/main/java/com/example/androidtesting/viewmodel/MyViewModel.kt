package com.example.androidtesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtesting.model.MyInterFace

class MyViewModel(private val myInterFace: MyInterFace) : ViewModel() {

    private val privateArea = MutableLiveData<String>()
    val area: LiveData<String>
        get() = privateArea


    private val privateCir = MutableLiveData<String>()
    val circular: LiveData<String>
        get() = privateCir


    fun getCalculate(radius: Double) {
        privateArea.value = myInterFace.getAreaDoubleValue(radius).toString()
        privateCir.value = myInterFace.getCircleDoubleValue(radius).toString()
    }

}