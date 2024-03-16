package com.example.splashscreencompose

import android.app.Application
import com.google.firebase.FirebaseApp

class SplashScreenCompose: Application()  {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}