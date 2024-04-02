package com.example.splashscreencompose.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_places")
data class FavouritePlaces(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val placeId: String
)
