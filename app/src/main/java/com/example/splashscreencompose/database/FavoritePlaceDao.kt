package com.example.splashscreencompose.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritePlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteSong(favouritePlaces: FavouritePlaces)

    @Query("DELETE FROM favorite_places WHERE placeId = :placeId")
    suspend fun deleteFavoriteSong(placeId: String)

    @Query("SELECT * FROM favorite_places")
    suspend fun getAllFavoriteSongs(): List<FavouritePlaces>

    @Query("SELECT * FROM favorite_places WHERE placeId = :placeId")
    fun isSongFavorite(placeId: String): FavouritePlaces?

    @Query("SELECT * FROM favorite_places WHERE placeId = :id")
    suspend fun getFavoriteSongById(id: Long) : FavouritePlaces

}