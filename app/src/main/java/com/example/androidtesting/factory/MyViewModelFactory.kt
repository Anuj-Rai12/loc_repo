package com.example.androidtesting.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtesting.model.MyCal
import com.example.androidtesting.viewmodel.MyViewModel


class MyViewModelFactory(private val myCal: MyCal): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MyViewModel(myCal) as T
    }
}
