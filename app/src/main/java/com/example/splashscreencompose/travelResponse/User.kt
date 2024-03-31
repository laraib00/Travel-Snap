package com.example.splashscreencompose.travelResponse

import java.io.Serializable

data class User(
    val avatar: Avatar,
    val member_id: String,
    val type: String,
    val user_id: String,
    val username: String
): Serializable