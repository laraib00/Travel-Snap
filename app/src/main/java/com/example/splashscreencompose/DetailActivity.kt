package com.example.splashscreencompose

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.splashscreencompose.databinding.ActivityDetailBinding
import com.example.splashscreencompose.travelResponse.Data

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }

        var model = intent.extras?.getSerializable("model") as Data

        binding.title.text = model.title
        binding.rating.text = "${model.rating}.0"
        binding.description.text = model.text
        binding.reviews.text = "${model.user.contributions.reviews} Reviews"
        binding.userLocation.text = model?.user?.user_location?.name ?: "Unknown User Location"

        val firstPhoto = model.photos?.firstOrNull()
        val locationName = firstPhoto?.locations?.firstOrNull()?.name ?: "Unknown Location"
        binding.location.text = locationName
        binding.publisher.text = model?.user?.name ?: "Publisher Not Found"

        if(!model.photos.isNullOrEmpty() && model.photos.firstOrNull()?.images?.original?.url != null) {
            Glide.with(this)
                .load(model.photos.firstOrNull()?.images?.original?.url!!)
                .placeholder(R.drawable.image)
                .centerCrop()
                .into(binding.image)
        }
    }
}