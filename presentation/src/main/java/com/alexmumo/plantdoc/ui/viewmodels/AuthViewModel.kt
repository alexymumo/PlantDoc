package com.alexmumo.plantdoc.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.alexmumo.data.repository.AuthRepository

class AuthViewModel constructor(private val authRepository: AuthRepository) : ViewModel()
