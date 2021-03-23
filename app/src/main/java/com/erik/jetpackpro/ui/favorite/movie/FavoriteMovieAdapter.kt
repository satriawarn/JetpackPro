package com.erik.jetpackpro.ui.favorite.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.erik.jetpackpro.BuildConfig
import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity
import com.erik.jetpackpro.databinding.ListItemBinding
import com.erik.jetpackpro.utils.loadImage

class FavoriteMovieAdapter(private val favoriteInterface: FavoriteInterface) :
        PagedListAdapter<MovieDetailEntity, FavoriteMovieAdapter.ItemsHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieDetailEntity>() {
            override fun areItemsTheSame(oldItem: MovieDetailEntity, newItem: MovieDetailEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieDetailEntity, newItem: MovieDetailEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieAdapter.ItemsHolder {
        val itemsMoviesBinding =
                ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemsHolder(itemsMoviesBinding)
    }

    override fun onBindViewHolder(holder: FavoriteMovieAdapter.ItemsHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

    fun getSwipedData(swipedPosition: Int): MovieDetailEntity? = getItem(swipedPosition)

    inner class ItemsHolder(private val binding: ListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieDetailEntity) {
            with(binding) {
                imgPoster.loadImage(BuildConfig.BASE_URL_POSTER_PATH_BIG + movie.posterPath)
                tvItemTitle.text = movie.title

                itemView.setOnClickListener {
                    favoriteInterface.onItemDetailClick(movie)

                }
            }
        }
    }
}