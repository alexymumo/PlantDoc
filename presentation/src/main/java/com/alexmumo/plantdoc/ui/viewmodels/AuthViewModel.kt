package com.alexmumo.plantdoc.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.alexmumo.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class AuthViewModel constructor(private val authRepository: AuthRepository) : ViewModel()
