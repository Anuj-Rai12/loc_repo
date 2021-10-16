package com.example.androidtesting.unit

sealed class MySealedFile<T>(
    val data: T? = null,
    val exception: Exception? = null
) {
    class Loading<T>(data: T?) : MySealedFile<T>(data)
    class Success<T>(data: T) : MySealedFile<T>(data)
    class Error<T>(data: T? = null, exception: Exception?) : MySealedFile<T>(data, exception)
}