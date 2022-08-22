package com.alexmumo.plantdoc.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexmumo.plantdoc.util.Resource
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun registerFarmer(fullName: String, email: String, phone: String, password: String): Resource<AuthResult>
    suspend fun signInFarmer(email: String, password: String): Resource<AuthResult>
    suspend fun forgotPassword(email: String): Resource<Any>
}

