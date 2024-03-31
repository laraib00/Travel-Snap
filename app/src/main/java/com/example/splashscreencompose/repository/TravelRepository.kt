package com.example.splashscreencompose.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.splashscreencompose.api.TravelApi
import com.example.splashscreencompose.model.ReviewRequest
import com.example.splashscreencompose.retrofit.RetrofitInstance
import com.example.splashscreencompose.travelResponse.TravelResponse
import com.example.splashscreencompose.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response

class TravelRepository(
)  {
    private val travelApi: TravelApi =  RetrofitInstance.api
    /*suspend fun getReviews(apiKey: String, requestBody: ReviewRequest): Response<TravelResponse> {
        return travelApi.getReviews(apiKey, requestBody)
    }*/

 /*   private val _reviewsResponse = MutableLiveData<Response<TravelResponse>>()
    val reviewsResponse: LiveData<Response<TravelResponse>>
        get() = _reviewsResponse

    suspend fun getReviews(apiKey: String, locationId: String, language: String, currency: String) {
        try {
            val response = travelApi.getReviews(apiKey, ReviewRequest(locationId,language, currency))
            _reviewsResponse.postValue(response)
        } catch (e: Exception) {
            // Handle errors
        }
    }*/

    private val _userResponseReviews = MutableLiveData<NetworkResult<TravelResponse>>()
    val userResponse: LiveData<NetworkResult<TravelResponse>>
        get() = _userResponseReviews

    suspend fun getReviews(apiKey: String, locationId: String, language: String, currency: String) {
          _userResponseReviews.postValue(NetworkResult.Loading())
        try {
            val response = travelApi.getReviews(apiKey, ReviewRequest(locationId,language, currency))
            if (response.isSuccessful) {
                val data = response.body()
                Log.d("ResultRepository", "Success: ${data}")
                _userResponseReviews.value = NetworkResult.Success(data)

            } else {
                // Handle unsuccessful response
                val errorObj = response.errorBody()?.string()?.let { JSONObject(it) }
                Log.d("ResultRepository", "Failure: ${errorObj?.getString("error")}")
                _userResponseReviews.value = NetworkResult.Error(errorObj?.getString("error"))
            }
        } catch (e: Exception) {
            // Handle exceptions
            _userResponseReviews.value = NetworkResult.Error(e.message ?: "Something went wrong")
        }
    }
}
