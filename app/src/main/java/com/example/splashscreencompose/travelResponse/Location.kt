package com.example.splashscreencompose.travelResponse

import java.io.Serializable

data class Location(
    val id: String,
    val intent_url: String,
    val name: String,
    val parent_location_id: Int,
    val web_url: String
): Serializable