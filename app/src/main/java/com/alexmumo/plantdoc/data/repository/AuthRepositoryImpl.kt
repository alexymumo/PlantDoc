package com.alexmumo.plantdoc.data.repository

import com.alexmumo.plantdoc.data.entity.User
import com.alexmumo.plantdoc.util.Resource
import com.alexmumo.plantdoc.util.safeCall
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepositoryImpl : AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("Farmer")
    override suspend fun registerFarmer(
        email: String,
        username: String,
        phone: String,
        password: String
    ): Resource<AuthResult> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val register = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                firebaseAuth.currentUser?.sendEmailVerification()?.await()
                val uid = register.user?.uid!!
                val user = User(uid, email, username, phone)
                firebaseDatabaseReference.child(uid).setValue(user).await()
                Resource.Success(register)
            }
        }
    }

    override suspend fun signInFarmer(email: String, password: String): Resource<AuthResult> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val sign = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                Resource.Success(sign)
            }
        }
    }

    override suspend fun forgotPassword(email: String): Resource<Any> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val forgot = firebaseAuth.sendPasswordResetEmail(email).await()
                Resource.Success(forgot)
            }
        }
    }
}
