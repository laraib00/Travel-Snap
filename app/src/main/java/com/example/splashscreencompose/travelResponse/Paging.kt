package com.example.splashscreencompose.travelResponse

import java.io.Serializable

data class Paging(
    val next: String,
    val previous: Any,
    val results: String,
    val skipped: String,
    val total_results: String
): Serializable