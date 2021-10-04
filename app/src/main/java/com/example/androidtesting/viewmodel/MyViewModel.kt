package com.example.androidtesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtesting.model.MyCal

class MyViewModel : ViewModel() {

    private val myCal:MyCal = MyCal()

    private val privateArea = MutableLiveData<String>()
    val area: LiveData<String>
        get() = privateArea


    private val privateCir = MutableLiveData<String>()
    val circular: LiveData<String>
        get() = privateCir


    fun getCalculate(radius:Double){
        privateArea.value=myCal.getAreaDoubleValue(radius).toString()
        privateCir.value=myCal.getCircleDoubleValue(radius).toString()
    }

}