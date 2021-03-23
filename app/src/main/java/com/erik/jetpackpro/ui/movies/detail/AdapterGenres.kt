package com.erik.jetpackpro.ui.movies.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erik.jetpackpro.data.source.remote.response.moviedetail.MovieGenres
import com.erik.jetpackpro.databinding.AdapterGenresBinding

class AdapterGenres(val data: List<MovieGenres>) :
        RecyclerView.Adapter<AdapterGenres.ViewHolder>() {

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
        fun bind(genres: MovieGenres) {
            with(binding) {
                tvNameGenres.text = genres.name
            }
        }
    }
}