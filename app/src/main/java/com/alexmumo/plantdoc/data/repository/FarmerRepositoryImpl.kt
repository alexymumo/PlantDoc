package com.alexmumo.plantdoc.data.repository

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

/*
* implementation of FarmerRepository interface
* */
class FarmerRepositoryImpl : FarmersRepository {
    private val firebaseStorage = Firebase.storage
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().reference

    override suspend fun getFarmers(): Resource<List<User>> {
        return withContext(Dispatchers.IO) {
            val uid = firebaseAuth.uid!!
            val farmersList = ArrayList<User>()
            safeCall {
                val farmers = databaseReference.child("farmers")
                val farmersLists = farmers.child(uid).get().await()
                for (i in farmersLists.children) {
                    val farmerResult = i.getValue(User::class.java)
                    farmersList.add(farmerResult!!)
                }
                Resource.Success(farmersList)
            }
        }
    }
}
