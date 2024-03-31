package com.example.splashscreencompose.api

import com.example.splashscreencompose.model.ReviewRequest
import com.example.splashscreencompose.travelResponse.TravelResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
 interface TravelApi {

    @POST("reviews")
    suspend fun getReviews(
        @Header("X-RapidAPI-Key") apiKey: String,
        @Body requestBody: ReviewRequest
    ): Response<TravelResponse>
}
