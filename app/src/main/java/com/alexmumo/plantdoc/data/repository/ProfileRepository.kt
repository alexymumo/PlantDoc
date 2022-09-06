package com.alexmumo.plantdoc.data.repository

import android.net.Uri
import com.alexmumo.plantdoc.util.Response
import kotlinx.coroutines.flow.Flow

/*
* Profile repository
* */
interface ProfileRepository {
    suspend fun uploadImage(imageUri: Uri): Flow<Response<Uri>>
    suspend fun fetchUserProfile(downloadUri: Uri): Flow<Response<Boolean>>
}
