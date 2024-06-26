package com.example.splashscreencompose.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.splashscreencompose.R
import com.example.splashscreencompose.SplashScreenCompose
import com.example.splashscreencompose.database.FavouritePlaces
import com.example.splashscreencompose.databinding.TravelItemBinding
import com.example.splashscreencompose.model.ReviewRequest
import com.example.splashscreencompose.model.TravelModelDemo
import com.example.splashscreencompose.travelResponse.Data
import com.example.splashscreencompose.travelResponse.TravelResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TravelAdapter(
    private val travelList: ArrayList<Data>,
    val context: Context,
    private val click: (position: Int, data: Data) -> Unit
) :
    RecyclerView.Adapter<TravelAdapter.TravelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        return TravelViewHolder(
            TravelItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        val travelItem = travelList[position]
        holder.bind(travelItem, position, click)
    }

    override fun getItemCount(): Int = travelList.size

    inner class TravelViewHolder(private val binding: TravelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        fun bind(data: Data, position: Int, click: (position: Int, data: Data) -> Unit) {
            binding.title.text = data.title
            binding.rating.text = "${data.rating}.0"
            binding.description.text = data.text

            CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
                val isFavorite = SplashScreenCompose.database.favoritePlaceDao().isSongFavorite(data.id)
                Log.d("favoriteSong", "song: $isFavorite id: ${isFavorite?.placeId}")
                withContext(Dispatchers.Main) {
                    if(isFavorite != null) {
                        binding.btnFavorite.setImageResource(R.drawable.favorite)

                    } else {
                        binding.btnFavorite.setImageResource(R.drawable.favorite_border)
                    }
                }
            }

            if (!data.photos.isNullOrEmpty() && data.photos.firstOrNull()?.images?.original?.url != null) {
                Glide.with(context)
                    .load(data.photos.firstOrNull()?.images?.original?.url)
                    .placeholder(R.drawable.image_placeholder)
                    .centerCrop()
                    .into(binding.image)
            }

            root.setOnClickListener {
                click(position, data)
            }

            binding.btnFavorite.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
                    val songFavorite =
                        SplashScreenCompose.database.favoritePlaceDao().isSongFavorite(data.id)
                    Log.d("favoriteSong", "song: $songFavorite id: ${songFavorite?.placeId}")
                    if (songFavorite != null) {
                        SplashScreenCompose.database.favoritePlaceDao().deleteFavoriteSong(songFavorite.placeId)
                        withContext(Dispatchers.Main) {
                            binding.btnFavorite.setImageResource(R.drawable.favorite_border)
                            /*FavouriteSongsFragment.favoriteSongs.remove(songFavorite)
                            FavouriteSongsFragment.favouriteSongsAdapter?.notifyDataSetChanged()
                            Log.e("index","$currentSongIndex")
                            FavouriteSongsFragment.favouriteSongsAdapter?.updateFavoriteSongList()*/
                            Toast.makeText(
                                context,
                                "Removed from favorites",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        val favouritePlaces = FavouritePlaces(
                            placeId = data.id
                        )
                        SplashScreenCompose.database.favoritePlaceDao().insertFavoriteSong(favouritePlaces)
                        withContext(Dispatchers.Main) {
                            binding.btnFavorite.setImageResource(R.drawable.favorite)

                            /*// Fetch all favorite songs and sort them by timestamp in descending order
                            FavouriteSongsFragment.favoriteSongs.addAll(
                                ApplicationClass.database.favoriteSongDao().getAllFavoriteSongs()
                                    .sortedByDescending { it.favouriteSongTimestamp } )
                            FavouriteSongsFragment.favouriteSongsAdapter?.updateFavoriteSongList()*/

                            Toast.makeText(
                                context,
                                "Added to favorites",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

   /* private fun updateStatusOfFavouritePlace(placeId: String) {
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            val isFavorite = SplashScreenCompose.database.favoritePlaceDao().isSongFavorite(placeId)
            withContext(Dispatchers.Main) {
                updateFavoriteButtonIcon(isFavorite != null)
            }
        }
    }
    private fun updateFavoriteButtonIcon(isFavorite: Boolean) {
        if (isFavorite) R.drawable.favorite
        else R.drawable.favorite_border
    }*/
}