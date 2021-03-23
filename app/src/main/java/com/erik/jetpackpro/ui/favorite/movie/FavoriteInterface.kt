package com.erik.jetpackpro.ui.favorite.movie

import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity

interface FavoriteInterface {
    fun onItemDetailClick(movie: MovieDetailEntity)
}