package com.alexmumo.plantdoc.util

inline fun <T : Any> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Error occurred")
    }
}
