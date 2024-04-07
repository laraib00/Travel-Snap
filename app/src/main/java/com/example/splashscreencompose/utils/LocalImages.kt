package com.example.splashscreencompose.utils

import java.io.Serializable

data class LocalImages(
    val id: Long,
    val title: String?,
    val url: String?,
    val size: Float,
    val modifiedDate: String,
    val mimeType: String,
    var delete: Boolean = false
) : Serializable
