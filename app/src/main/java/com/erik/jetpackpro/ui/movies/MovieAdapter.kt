package com.erik.jetpackpro.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.erik.jetpackpro.BuildConfig
import com.erik.jetpackpro.data.source.local.entity.MovieEntity
import com.erik.jetpackpro.databinding.ListItemBinding
import com.erik.jetpackpro.utils.loadImage

class MovieAdapter(private val movieInterface: MovieInterface) :
        PagedListAdapter<MovieEntity, MovieAdapter.ItemsHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>(){
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsHolder {
        val itemsMoviesBinding =
                ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemsHolder(itemsMoviesBinding)
    }

    override fun onBindViewHolder(holder: ItemsHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

    inner class ItemsHolder(private val binding: ListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                imgPoster.loadImage(BuildConfig.BASE_URL_POSTER_PATH_BIG + movie.posterPath)
                tvItemTitle.text = movie.title

                itemView.setOnClickListener {
                    movieInterface.onItemClick(movie)
                }
            }
        }
    }
}