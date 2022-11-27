package com.alexmumo.plantdoc.data.entity

data class Plant(
    val diseaseId: String?,
    val disease: String? = "",
    val confidence: String? = ""
)

// display marker on map using realtime database
// https://www.youtube.com/watch?v=4Z9X4Z9X4Z9
