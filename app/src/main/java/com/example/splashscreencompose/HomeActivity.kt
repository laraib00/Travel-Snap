package com.example.splashscreencompose

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splashscreencompose.adapter.TravelAdapter
import com.example.splashscreencompose.databinding.ActivityHomeBinding
import com.example.splashscreencompose.model.ReviewRequest
import com.example.splashscreencompose.model.TravelModelDemo
import com.example.splashscreencompose.repository.TravelRepository
import com.example.splashscreencompose.travelResponse.Data
import com.example.splashscreencompose.travelResponse.TravelResponse
import com.example.splashscreencompose.utils.Constants
import com.example.splashscreencompose.utils.NetworkResult
import com.example.splashscreencompose.viewModel.TravelViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: TravelAdapter
    private var travelList = ArrayList<Data>()
    private val repository = TravelRepository()
    private val travelViewModel = TravelViewModel(repository)

    private var isFirstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locationIds = listOf("8797440", "47087", "45963", "562820", "187472", "259440", "150802", "3194809", "664450", "147288")
        var currentIndex = 0

        fetchDataForLocationId(locationIds[currentIndex])

        binding.swipe.setOnRefreshListener {

            isFirstTime = false
            // Increment the index cyclically
            currentIndex = (currentIndex + 1) % locationIds.size
            // Make API call with the location ID at the next index
            fetchDataForLocationId(locationIds[currentIndex])
            Log.d("locationId", locationIds[currentIndex])
        }

        // Configure the refreshing colors
        binding.swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)


         reviewObserver()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun reviewObserver() {
        travelViewModel.getReviewResponse.observe(this) {
            Log.d("observer", "reviewObserver: $it")
            when (it) {
                is NetworkResult.Success -> {
                    Log.d("Result", "called")
                    binding.progress.isVisible = false
                    binding.swipe.isRefreshing = false

                    // Add new data to the top of the travelList
                    it.data?.results?.data?.let { newData ->
                        travelList.addAll(0, newData)
                    }

                    Log.d("Result", travelList.toString())
                    if (travelList != null) {
                        if(isFirstTime) {
                            adapter = TravelAdapter(travelList, this) { position, model ->
                                val bundle = Bundle()
                                bundle.putSerializable("model", model)
                                val intent = Intent(this, DetailActivity::class.java)
                                intent.putExtras(bundle)
                                startActivity(intent)
                            }
                            binding.travelRecyclerView.layoutManager = LinearLayoutManager(this)
                            binding.travelRecyclerView.adapter = adapter
                        } else {
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Log.d("Result", it.message.toString())
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    binding.swipe.isRefreshing = false
                }
                is NetworkResult.Loading -> {
                        binding.progress.isVisible = true
                }

            }
        }
    }

    private fun fetchDataForLocationId(locationId: String) {
        travelViewModel.getReviews(
            Constants.API_KEY,
            locationId,
            language = "en_US",
            currency = "USD"
        )
    }
}