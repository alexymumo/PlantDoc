package com.alexmumo.plantdoc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexmumo.plantdoc.data.entity.User
import com.alexmumo.plantdoc.data.repository.FarmersRepository
import com.alexmumo.plantdoc.util.Event
import com.alexmumo.plantdoc.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FarmersViewModel @Inject constructor(
    private val farmersRepository: FarmersRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    private var _farmers = MutableLiveData<Event<Resource<List<User>>>>()
    val farmer: LiveData<Event<Resource<List<User>>>> = _farmers

    init {
        getFarmersList()
    }

    private fun getFarmersList() {
        _farmers.postValue(Event(Resource.Loading()))
        viewModelScope.launch {
            val farmers = farmersRepository.getFarmers()
            _farmers.postValue(Event(farmers))
        }
    }
}
