package com.example.splashscreencompose.travelResponse

import java.io.Serializable

data class Images(
    val large: Large,
    val medium: Medium,
    val original: Original,
    val small: Small,
    val thumbnail: Thumbnail
): Serializable