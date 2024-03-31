package com.example.splashscreencompose.travelResponse

import java.io.Serializable

data class Contributions(
    val attraction_reviews: String,
    val badges_count: String,
    val helpful_votes: String,
    val hotel_reviews: String,
    val photos_count: String,
    val restaurant_reviews: String,
    val review_city_count: String,
    val reviews: String
): Serializable