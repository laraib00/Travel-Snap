package com.example.splashscreencompose.travelResponse

import java.io.Serializable

data class Photo(
    val caption: String,
    val helpful_votes: String,
    val id: String,
    val images: Images,
    val is_blessed: Boolean,
    val locations: List<Location>,
    val published_date: String,
    val uploaded_date: String,
    val user: User
): Serializable