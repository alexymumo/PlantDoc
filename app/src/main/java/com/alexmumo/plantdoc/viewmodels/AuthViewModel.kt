package com.alexmumo.plantdoc.viewmodels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexmumo.plantdoc.data.repository.AuthRepository
import com.alexmumo.plantdoc.util.Event
import com.alexmumo.plantdoc.util.Resource
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

    fun registerFarmer(fullname: String, email: String, phone: String, password: String) {
        val error = if (fullname.isEmpty() || email.isEmpty() || password.isEmpty() || password.isEmpty()) {
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
            val register = authRepository.registerFarmer(fullname, email, phone, password)
            _signup.postValue(Event(register))
        }
    }
    fun signInUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _login.postValue(Event(Resource.Error("Cannot be empty")))
        } else {
            _login.postValue(Event(Resource.Loading()))
            viewModelScope.launch {
                val result = authRepository.signInFarmer(email, password)
                _login.postValue(Event(result))
            }
        }
    }
}
