package com.example.splashscreencompose.travelResponse

import java.io.Serializable

data class Avatar(
    val caption: String,
    val helpful_votes: String,
    val id: String,
    val images: ImagesX,
    val is_blessed: Boolean,
    val published_date: String,
    val uploaded_date: String
): Serializable