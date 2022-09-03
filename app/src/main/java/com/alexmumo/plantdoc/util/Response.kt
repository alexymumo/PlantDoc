package com.alexmumo.plantdoc.util

sealed class Response<out T> {
    object Loading : Response<Nothing>()
    data class Success<T>(
        val data: T?
    ) : Response<T>()

    data class Failure<T> (
        val e: Exception
    ) : Response<T>()
}
