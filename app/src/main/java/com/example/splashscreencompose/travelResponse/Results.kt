package com.example.splashscreencompose.travelResponse

import java.io.Serializable

data class Results(
    val `data`: List<Data>,
    val paging: Paging
): Serializable