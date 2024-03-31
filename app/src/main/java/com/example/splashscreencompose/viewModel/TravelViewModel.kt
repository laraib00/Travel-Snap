package com.example.splashscreencompose.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.splashscreencompose.model.ReviewRequest
import com.example.splashscreencompose.repository.TravelRepository
import com.example.splashscreencompose.travelResponse.TravelResponse
import com.example.splashscreencompose.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class TravelViewModel(
    private val travelRepository: TravelRepository
): ViewModel() {

    val getReviewResponse: LiveData<NetworkResult<TravelResponse>>
        get() = travelRepository.userResponse

    fun getReviews(apiKey: String, locationId: String, language: String, currency: String) {
        viewModelScope.launch {
            travelRepository.getReviews(apiKey,locationId,language, currency)
        }
    }

   /* fun getReviews(apiKey: String, requestBody: ReviewRequest): Flow<TravelResponse?> = flow {
        val response = travelRepository.getReviews(apiKey, requestBody)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            // Handle error
            emit(null)
        }
    }*/
}