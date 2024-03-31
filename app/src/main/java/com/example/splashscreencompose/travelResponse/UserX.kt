package com.example.splashscreencompose.travelResponse

import java.io.Serializable

data class UserX(
    val avatar: AvatarX,
    val contributions: Contributions,
    val created_time: String,
    val first_name: String,
    val last_initial: String,
    val link: String,
    val locale: String,
    val member_id: String,
    val name: String,
    val points: String,
    val reviewer_type: Any,
    val type: String,
    val user_id: String,
    val user_location: UserLocation,
    val username: String
): Serializable