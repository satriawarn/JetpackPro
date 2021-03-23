package com.erik.jetpackpro.ui.tvshows.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erik.jetpackpro.data.source.remote.response.tvdetail.GenresItem
import com.erik.jetpackpro.databinding.AdapterGenresBinding

class AdapterTvGenres(val data: List<GenresItem>) :
        RecyclerView.Adapter<AdapterTvGenres.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemsGenresBinding =
                AdapterGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsGenresBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genre = data[position]
        holder.bind(genre)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(private val binding: AdapterGenresBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(genres: GenresItem) {
            with(binding) {
                tvNameGenres.text = genres.name
            }
        }
    }
}