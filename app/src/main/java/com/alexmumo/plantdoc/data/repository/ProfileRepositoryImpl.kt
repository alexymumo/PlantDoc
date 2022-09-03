package com.alexmumo.plantdoc.data.repository

import android.net.Uri
import com.alexmumo.plantdoc.util.Resource
import com.alexmumo.plantdoc.util.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
* */
class ProfileRepositoryImpl(
    private val firebaseStorage: FirebaseStorage = Firebase.storage,
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
) : ProfileRepository {
    override suspend fun uploadImage(imageUri: Uri): Flow<Response<Uri>> = flow {
        emit(Response.Loading)
        try {
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }

    override suspend fun fetchUserProfile(downloadUri: Uri): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}
