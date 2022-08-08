package com.alexmumo.plantdoc.data.repository

import android.net.Uri
import com.alexmumo.plantdoc.data.entity.User
import com.alexmumo.plantdoc.util.Resource

interface UserRepository {
    suspend fun saveUserProfile(uri: Uri): Resource<Any>
    suspend fun getCurrentUserProfile(): Resource<User>
}
