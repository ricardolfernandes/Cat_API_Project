package com.ricardolfernandes.catapi.utils

sealed class States<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : States<T>(data)
    class Error<T>(message: String, data: T? = null) : States<T>(data, message)
    class Loading<T> : States<T>()
}