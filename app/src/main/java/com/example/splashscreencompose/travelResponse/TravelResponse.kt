package com.example.splashscreencompose.travelResponse

import java.io.Serializable

data class TravelResponse(
    val msg: Any,
    val results: Results,
    val status: Int
): Serializable