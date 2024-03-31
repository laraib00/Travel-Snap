package com.example.splashscreencompose.travelResponse

import java.io.Serializable

data class Data(
    val helpful_votes: String,
    val id: String,
    val lang: String,
    val location_id: String,
    val machine_translatable: Boolean,
    val machine_translated: Boolean,
    val owner_response: Any,
    val photos: List<Photo?>,
    val published_date: String,
    val published_platform: String,
    val rating: String,
    val subratings: List<Any>,
    val text: String,
    val title: String,
    val travel_date: String,
    val type: String,
    val url: String,
    val user: UserX
): Serializable