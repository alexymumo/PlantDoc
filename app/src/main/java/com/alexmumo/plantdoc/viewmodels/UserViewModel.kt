package com.alexmumo.plantdoc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexmumo.plantdoc.data.entity.User
import com.alexmumo.plantdoc.data.repository.UserRepository
import com.alexmumo.plantdoc.util.Event
import com.alexmumo.plantdoc.util.Resource
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository) {
    private var _user = MutableLiveData<Event<Resource<User>>>()
    val user: LiveData<Event<Resource<User>>> = _user

    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        TODO("Not yet implemented")
    }
}