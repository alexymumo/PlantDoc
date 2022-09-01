package com.alexmumo.plantdoc.data.repository

import com.alexmumo.plantdoc.util.Resource
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun registerFarmer(fullName: String, email: String, phone: String, password: String): Resource<AuthResult>
    suspend fun signInFarmer(email: String, password: String): Resource<AuthResult>
    suspend fun forgotPassword(email: String): Resource<Any>
}

