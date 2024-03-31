package com.example.splashscreencompose.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewRequest(
    @Json(name = "location_id")
    val location_id: String,
    @Json(name = "language")
    val language: String,
    @Json(name = "currency")
    val currency: String
)
