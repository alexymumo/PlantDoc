package com.alexmumo.plantdoc.data.repository

import com.alexmumo.plantdoc.data.entity.User
import com.alexmumo.plantdoc.util.Resource

interface FarmersRepository {
    suspend fun getFarmers(): Resource<List<User>>
}
