package com.example.androidtesting.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtesting.db.LocDao

class ViewModelProvider(
    private val locDao: LocDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ViewModel(locDao) as T
    }
}