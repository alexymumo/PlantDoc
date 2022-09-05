package com.alexmumo.plantdoc.data.repository

import android.net.Uri
import com.alexmumo.plantdoc.data.entity.User
import com.alexmumo.plantdoc.util.Resource
import com.alexmumo.plantdoc.util.safeCall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepositoryImpl : UserRepository {
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val firebaseStorage = Firebase.storage
    private val firebaseAuth = FirebaseAuth.getInstance()

    override suspend fun saveUserProfile(uri: Uri): Resource<Any> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val uid = firebaseAuth.uid!!
                val result = firebaseStorage.getReference(uid).putFile(uri).await()
                val imageUrl = result?.metadata?.reference?.downloadUrl?.await().toString()
                databaseReference.child(uid).child("profile").setValue(imageUrl)
                Resource.Success(Any())
            }
        }
    }

    override suspend fun getCurrentUserProfile(): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val uid = firebaseAuth.uid!!
                val currentProfile = databaseReference.child("farmers").child(uid).get().await()
                    .getValue(User::class.java) ?: throw IllegalArgumentException()
                Resource.Success(currentProfile)
            }
        }
    }
}
