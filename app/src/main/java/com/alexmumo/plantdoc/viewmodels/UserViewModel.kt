package com.alexmumo.plantdoc.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexmumo.plantdoc.data.entity.User
import com.alexmumo.plantdoc.data.repository.UserRepository
import com.alexmumo.plantdoc.util.Event
import com.alexmumo.plantdoc.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    private var _user = MutableLiveData<Event<Resource<User>>>()
    val user: LiveData<Event<Resource<User>>> = _user

    init {
        getUserProfile()
    }
    private val _curlImageUri = MutableLiveData<Uri>()
    val curlImageUri: LiveData<Uri> = _curlImageUri

    fun setImageUri(uri: Uri) {
        _curlImageUri.postValue(uri)
    }
    private fun getUserProfile() {
        _user.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val profile = userRepository.getCurrentUserProfile()
            _user.postValue(Event(profile))
        }
    }
}
