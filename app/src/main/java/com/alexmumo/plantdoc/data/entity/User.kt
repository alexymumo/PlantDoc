package com.alexmumo.plantdoc.data.entity

data class User(
    val uid: String,
    val name: String,
    val email: String,
    val password: String,
    val location: String
)
