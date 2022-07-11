package com.alexmumo.plantdoc.util

import com.alexmumo.data.util.Resource

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Error occurred")
    }
}
