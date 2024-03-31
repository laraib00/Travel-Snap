package com.example.splashscreencompose.retrofit

import com.example.splashscreencompose.api.TravelApi
import com.example.splashscreencompose.utils.Constants.BASE_URL_TRAVEL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_TRAVEL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: TravelApi by lazy {
        retrofit.create(TravelApi::class.java)
    }
}