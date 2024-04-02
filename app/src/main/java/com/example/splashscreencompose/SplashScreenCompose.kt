package com.example.splashscreencompose

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.splashscreencompose.database.AppDatabase
import com.google.firebase.FirebaseApp

class SplashScreenCompose: Application()  {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "TravelSnapDB")
            .fallbackToDestructiveMigration()
            .build()
    }
}