package com.alexmumo.plantdoc.ui.fragments.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexmumo.data.repository.AuthRepository
import com.alexmumo.data.util.Resource
import com.alexmumo.plantdoc.util.Event
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch

class AuthViewModel constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _signup = MutableLiveData<Event<Resource<AuthResult>>>()
    val register: LiveData<Event<Resource<AuthResult>>> = _signup

    private val _login = MutableLiveData<Event<Resource<AuthResult>>>()
    val login: LiveData<Event<Resource<AuthResult>>> = _login

    fun registerUser(name: String, email: String, password: String, location: String) {
        viewModelScope.launch {
            val register = authRepository.registerUser(name, email, password, location)
            _signup.postValue(Event(register))
        }
    }
    fun signInUser(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.signIn(email, password)
            _login.postValue(Event(result))
        }
    }
}
