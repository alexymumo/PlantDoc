package com.alexmumo.plantdoc.ui.fragments.auth

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexmumo.plantdoc.data.repository.AuthRepository
import com.alexmumo.plantdoc.util.Resource
import com.alexmumo.plantdoc.util.Event
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    private val _signup = MutableLiveData<Event<Resource<AuthResult>>>()
    val register: LiveData<Event<Resource<AuthResult>>> = _signup

    private val _login = MutableLiveData<Event<Resource<AuthResult>>>()
    val login: LiveData<Event<Resource<AuthResult>>> = _login

    fun registerUser(name: String, email: String, password: String, location: String) {
        var error = if (name.isEmpty() || email.isEmpty() || password.isEmpty() || location.isEmpty()) {
            "Cannot be Empty"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Not Valid"
        } else null
        error?.let {
            _signup.postValue(Event(Resource.Error(it)))
            return
        }
        _signup.postValue(Event(Resource.Loading()))
        viewModelScope.launch {
            val register = authRepository.registerUser(name, email, password, location)
            _signup.postValue(Event(register))
        }
    }
    fun signInUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _login.postValue(Event(Resource.Error("Cannot be empty")))
        } else {
            _login.postValue(Event(Resource.Loading()))
            viewModelScope.launch {
                val result = authRepository.signIn(email, password)
                _login.postValue(Event(result))
            }
        }
    }
}
