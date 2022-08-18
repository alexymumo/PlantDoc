package com.alexmumo.plantdoc

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlantApplication : Application()

class User<T>(name: T) {
    var user = name
    init {
        println(user)
    }
}

fun main() {
    val alex: User<String> = User<String>("alexmumo")
    val mumo: User<Int> = User<Int>(1)
}


