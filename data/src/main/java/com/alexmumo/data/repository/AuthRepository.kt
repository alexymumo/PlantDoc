package com.alexmumo.data.repository

import com.alexmumo.data.entity.User
import com.alexmumo.data.util.Resource
import com.alexmumo.data.util.safeCall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseReference = FirebaseDatabase.getInstance().getReference("farmers")

    suspend fun registerUser(name: String, email: String, password: String, location: String) {
        return withContext(Dispatchers.IO) {
            safeCall {
                val register = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                val uid = register.user?.uid!!
                val user = User(name, email, password, location, uid)
                firebaseReference.child(uid).setValue(user).await()
                Resource.Success(register)
            }
        }
    }

    suspend fun signIn(email: String, password: String) {
        return withContext(Dispatchers.IO) {
            val login = firebaseAuth.signInWithEmailAndPassword(email, password)
            Resource.Success(login)
        }
    }
}
