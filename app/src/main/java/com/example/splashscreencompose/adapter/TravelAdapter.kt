package com.example.splashscreencompose.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.splashscreencompose.R
import com.example.splashscreencompose.databinding.TravelItemBinding
import com.example.splashscreencompose.model.ReviewRequest
import com.example.splashscreencompose.model.TravelModelDemo
import com.example.splashscreencompose.travelResponse.Data
import com.example.splashscreencompose.travelResponse.TravelResponse

class TravelAdapter(
    private val travelList: ArrayList<Data>,
    val context: Context,
    private val click: (position: Int, data: Data) -> Unit
):
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
        holder.bind(travelItem, position ,click)
    }

    override fun getItemCount(): Int = travelList.size

    inner class TravelViewHolder(private val binding: TravelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        fun bind(data: Data, position: Int,click: (position: Int, data: Data) -> Unit) {
            binding.title.text = data.title
            binding.rating.text = "${data.rating}.0"
            binding.description.text = data.text

            if(!data.photos.isNullOrEmpty() && data.photos.firstOrNull()?.images?.original?.url != null) {
                Glide.with(context)
                    .load(data.photos.firstOrNull()?.images?.original?.url)
                    .placeholder(R.drawable.image_placeholder)
                    .centerCrop()
                    .into(binding.image)
            }

            root.setOnClickListener {
                click(position, data)
            }
        }
    }
}